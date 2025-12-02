package com.example.huertohogar_mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import com.example.huertohogar_mobile.navigation.NavigationEvent
import com.example.huertohogar_mobile.navigation.Screen
import com.example.huertohogar_mobile.navigation.Screen.*

//ViewModel principal que gestiona los eventos de navegación y el estado de autenticación global.

class MainViewModel : ViewModel() {

    // GESTIÓN DEL ESTADO DE AUTENTICACIÓN

    // 1. Estado Mutable Privado (Inicialmente false)
    private val _isLoggedIn = MutableStateFlow(false)

    // 2. Estado Público Reactivo (La vista ProfileScreen observa este estado)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    // Actualiza el estado de inicio de sesión. Llamado por UsuarioViewModel tras el registro.
    fun setLoggedIn(state: Boolean) {
        _isLoggedIn.value = state
    }


    // GESTIÓN DE LA NAVEGACIÓN
    private val _navigationEvents = Channel<NavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()


    //  Función para emitir el evento de navegación hacia la ruta deseada
    fun navigateTo(
        route: Screen,
        popUpToRoute: Screen? = null,
        inclusive: Boolean = false,
        singleTop: Boolean = false
    ) {
        viewModelScope.launch {
            _navigationEvents.send(
                NavigationEvent.NavigateTo(
                    route = route,
                    popUpToRoute = popUpToRoute,
                    inclusive = inclusive,
                    singleTop = singleTop
                )
            )
        }
    }

    // Función para volver a la pantalla anterior
    fun navigateBack() {
        viewModelScope.launch {
            _navigationEvents.send(NavigationEvent.PopBackstack)
        }
    }

    //  Función para navegar hacia arriba (padre).
    fun navigateUp() {
        viewModelScope.launch {
            _navigationEvents.send(NavigationEvent.NavigateUp)
        }
    }
}