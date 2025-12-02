package com.example.huertohogar_mobile.viewmodel

import android.app.Application
import com.example.huertohogar_mobile.ui.repository.UserData
import com.example.huertohogar_mobile.ui.repository.UserDataStore
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsuarioViewModelTest {

    private val application: Application = mockk(relaxed = true)
    // Usamos un Mock para evitar el error de archivo nulo en Windows
    private val dataStore: UserDataStore = mockk(relaxed = true)

    private lateinit var viewModel: UsuarioViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Simulamos que la base de datos está vacía al inicio
        coEvery { dataStore.userData } returns flowOf(UserData("", "", "", "", ""))

        // Inyectamos el Mock
        viewModel = UsuarioViewModel(application, dataStore)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `validarFormulario retorna false si los campos estan vacios`() = runTest {
        val esValido = viewModel.validarFormulario()
        assertFalse("El formulario vacío no debería ser válido", esValido)
        assertNotNull("Debe haber error en nombre", viewModel.estado.value.errores.nombre)
    }

    @Test
    fun `validarFormulario detecta correo invalido`() = runTest {
        viewModel.onNombreChange("Juan Perez")
        viewModel.onCorreoChange("correo_sin_arroba.com")
        viewModel.onClaveChange("123456")
        viewModel.onDireccionChange("Calle Falsa 123")
        viewModel.onTelefonoChange("912345678")

        val esValido = viewModel.validarFormulario()
        assertFalse(esValido)
        assertEquals("Correo inválido", viewModel.estado.value.errores.correo)
    }

    @Test
    fun `validarFormulario detecta telefono incompleto`() = runTest {
        // En este test, ya llamas a onDireccionChange y onNombreChange,
        // lo cual es bueno para asegurar que los campos existan en el estado antes de validar.
        viewModel.onTelefonoChange("123")

        viewModel.onNombreChange("Juan")
        viewModel.onCorreoChange("juan@gmail.com")
        viewModel.onClaveChange("123456")
        viewModel.onDireccionChange("Casa")

        val resultado = viewModel.validarFormulario()
        assertFalse(resultado)
        assertNotNull(viewModel.estado.value.errores.telefono)
    }

    @Test
    fun `onAceptarTerminosChange actualiza el estado`() = runTest {
        viewModel.onAceptarTerminosChange(true)
        assertTrue(viewModel.estado.value.aceptaTerminos)
    }
}