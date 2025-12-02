package com.example.huertohogar_mobile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import com.example.huertohogar_mobile.ui.repository.EstadoDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.delay

// ViewModel para la demostración de persistencia y estado avanzado
class AnimationDemoViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = EstadoDataStore(application.applicationContext)

    //  Estados
    var count by mutableIntStateOf(0)
        private set

    val isToggled: StateFlow<Boolean?> = dataStore.obtenerEstado()
        .map { it ?: false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _mostrarMensaje = MutableStateFlow(false)
    val mostrarMensaje: StateFlow<Boolean> = _mostrarMensaje.asStateFlow()

    // LÓGICA DE EVENTOS Y PERSISTENCIA
    fun toggleState() {
        val nuevoValor = isToggled.value?.not() ?: true
        viewModelScope.launch {
            // 1. Guardar el estado
            dataStore.guardarEstado(nuevoValor)

            // ✅ CORRECCIÓN FINAL: Lógica del Feedback Animado
            _mostrarMensaje.value = true // Activar la visibilidad
            delay(2000) // Esperar 2 segundos (tiempo de visualización)
            _mostrarMensaje.value = false // Desactivar la visibilidad (con animación fluida)
        }
    }

    fun increment() {
        count += 1
    }
}