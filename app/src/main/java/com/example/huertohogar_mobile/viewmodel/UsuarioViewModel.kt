package com.example.huertohogar_mobile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar_mobile.ui.model.UsuarioErrores
import com.example.huertohogar_mobile.ui.model.UsuarioUiState
import com.example.huertohogar_mobile.ui.repository.UserDataStore
import com.example.huertohogar_mobile.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// 1. Recibimos dataStoreInjected (opcional)
// Esto permite que el Test le pase una base de datos falsa y no falle
class UsuarioViewModel(
    application: Application,
    private val dataStoreInjected: UserDataStore? = null
) : AndroidViewModel(application) {

    // 2. LÓGICA
    private val dataStore = dataStoreInjected ?: UserDataStore(application.applicationContext)

    // Constructor secundario obligatorio para que Android pueda crear el ViewModel
    constructor(application: Application) : this(application, null)

    private val _estado = MutableStateFlow(UsuarioUiState())
    val estado: StateFlow<UsuarioUiState> = _estado.asStateFlow()

    init {
        // Cargar datos al iniciar
        viewModelScope.launch {
            try {
                dataStore.userData.collect { storedUser ->
                    if (storedUser.correo.isNotEmpty()) {
                        _estado.update {
                            it.copy(
                                nombre = storedUser.nombre,
                                correo = storedUser.correo,
                                clave = storedUser.clave,
                                direccion = storedUser.direccion,
                                telefono = storedUser.telefono
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                // Ignoramos errores de contexto en tests
            }
        }
    }

    // FUNCIONES DE FORMULARIO
    fun onNombreChange(valor: String) { _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) } }
    fun onCorreoChange(valor: String) { _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) } }
    fun onClaveChange(valor: String) { _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) } }
    fun onDireccionChange(valor: String) { _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) } }
    fun onAceptarTerminosChange(valor: Boolean) { _estado.update { it.copy(aceptaTerminos = valor) } }
    fun onTelefonoChange(valor: String) {
        val clean = valor.filter { it.isDigit() }
        if (clean.length <= 9) _estado.update { it.copy(telefono = clean, errores = it.errores.copy(telefono = null)) }
    }

    fun validarFormulario(): Boolean {
        val e = _estado.value
        val errores = UsuarioErrores(
            nombre = if (e.nombre.isBlank()) "Campo obligatorio" else null,
            correo = if (!e.correo.contains("@")) "Correo inválido" else null,
            clave = if (e.clave.length < 6) "Mínimo 6 caracteres" else null,
            direccion = if (e.direccion.isBlank()) "Campo obligatorio" else null,
            telefono = if (e.telefono.length != 9) "Debe tener 9 dígitos" else null
        )
        _estado.update { it.copy(errores = errores) }
        return errores == UsuarioErrores()
    }

    // REGISTRAR USUARIO
    fun onRegistroSubmit(mainViewModel: MainViewModel) {
        if (validarFormulario()) {
            if (!_estado.value.aceptaTerminos) return

            viewModelScope.launch {
                _estado.update { it.copy(isLoading = true) }
                delay(1000)

                // Guardar en DataStore
                dataStore.saveUser(
                    nombre = _estado.value.nombre,
                    correo = _estado.value.correo,
                    clave = _estado.value.clave,
                    direccion = _estado.value.direccion,
                    telefono = _estado.value.telefono
                )

                _estado.update { it.copy(isLoading = false, isRegistrationSuccessful = true) }
                mainViewModel.setLoggedIn(true)
                mainViewModel.navigateTo(Screen.Profile, popUpToRoute = Screen.Registro, inclusive = true)
            }
        }
    }

    // 3. ACTUALIZAR DATOS
    fun actualizarDatos(nuevoNombre: String, nuevaDireccion: String, nuevoTelefono: String) {
        viewModelScope.launch {
            val usuarioActual = _estado.value

            dataStore.saveUser(
                nombre = nuevoNombre,
                direccion = nuevaDireccion,
                telefono = nuevoTelefono,
                correo = usuarioActual.correo,
                clave = usuarioActual.clave
            )

            _estado.update {
                it.copy(nombre = nuevoNombre, direccion = nuevaDireccion, telefono = nuevoTelefono)
            }
        }
    }

    // INICIAR SESIÓN
    fun performLogin(emailInput: String, passInput: String, mainViewModel: MainViewModel, onError: (String) -> Unit) {
        viewModelScope.launch {
            _estado.update { it.copy(isLoading = true) }
            delay(1000)

            val storedUser = dataStore.userData.first()

            if (storedUser.correo.isNotEmpty() && storedUser.correo == emailInput && storedUser.clave == passInput) {
                _estado.update {
                    it.copy(
                        nombre = storedUser.nombre,
                        correo = storedUser.correo,
                        clave = storedUser.clave,
                        direccion = storedUser.direccion,
                        telefono = storedUser.telefono
                    )
                }
                mainViewModel.setLoggedIn(true)
            } else {
                val mensaje = if (storedUser.correo.isEmpty()) "No hay usuarios registrados" else "Correo o contraseña incorrectos"
                onError(mensaje)
            }
            _estado.update { it.copy(isLoading = false) }
        }
    }

    fun cerrarSesion(mainViewModel: MainViewModel) {
        mainViewModel.setLoggedIn(false)
    }
}