package com.example.huertohogar_mobile.ui.model

//Modelo que almacena posibles errores individuales del formulario.

data class UsuarioErrores(
    val nombre: String? = null, // Error de validación de Nombre
    val correo: String? = null, // Error de validación de Correo
    val clave: String? = null,  // Error de validación de Clave
    val direccion: String? = null,
    val telefono: String? = null// Error de validación de Dirección
)