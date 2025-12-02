package com.example.huertohogar_mobile.navigation

// Sealed Class que representa los distintos tipos de eventos de navegación.
// Esta clase es usada por el ViewModel para comunicarse con el NavController
sealed class NavigationEvent {

    //Evento para navegar a un destino específico.
    data class NavigateTo(
        val route: Screen,
        val popUpToRoute: Screen? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false
    ) : NavigationEvent()

    //Evento para volver a la pantalla anterior

    data object PopBackstack : NavigationEvent()

    data object NavigateUp : NavigationEvent()
}