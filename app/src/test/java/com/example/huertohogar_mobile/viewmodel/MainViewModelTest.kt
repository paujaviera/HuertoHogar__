package com.example.huertohogar_mobile.viewmodel

import com.example.huertohogar_mobile.navigation.NavigationEvent
import com.example.huertohogar_mobile.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `estado inicial de login debe ser false`() = runTest {
        assertFalse(viewModel.isLoggedIn.value)
    }

    @Test
    fun `setLoggedIn actualiza el estado de sesion`() = runTest {
        viewModel.setLoggedIn(true)
        assertTrue(viewModel.isLoggedIn.value)

        viewModel.setLoggedIn(false)
        assertFalse(viewModel.isLoggedIn.value)
    }

    @Test
    fun `MapsTo emite el evento correcto`() = runTest {
        // Ejecutamos la navegación
        viewModel.navigateTo(Screen.Settings)

        // Capturamos el primer evento emitido
        val evento = viewModel.navigationEvents.first()

        // Verificamos que sea del tipo NavigateTo y a la ruta correcta
        assertTrue(evento is NavigationEvent.NavigateTo)
        assertEquals(Screen.Settings, (evento as NavigationEvent.NavigateTo).route)
    }
}