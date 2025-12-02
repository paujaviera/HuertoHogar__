package com.example.huertohogar_mobile.ui.model

//Modelo de datos para productos

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int, // Referencia a la imagen local
    val isOrganic: Boolean = true,
    val category: String
)