package com.example.huertohogar_mobile.ui.model

// Modelo principal que representa el estado del formulario de registro.
data class UsuarioUiState(
    // Datos del formulario
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val direccion: String = "",
    val telefono: String = "",
    val aceptaTerminos: Boolean = false, // Confirmación de términos

    // Manejo de errores
    val errores: UsuarioErrores = UsuarioErrores(), // Objeto que contiene los errores por campo

    // Indicadores de la UI
    val isLoading: Boolean = false,
    val isRegistrationSuccessful: Boolean = false
)