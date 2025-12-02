package com.example.huertohogar_mobile.repository

import com.example.huertohogar_mobile.data.model.Post
import com.example.huertohogar_mobile.data.remote.RetrofitInstance

class PostRepository {

    // Función que llama a la API y devuelve la lista de posts
    suspend fun getPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }
}
