package com.example.huertohogar_mobile.data.model

// Modelo que representa un "post" de la API jsonplaceholder
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
