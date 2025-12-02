package com.example.huertohogar_mobile.data.remote

import com.example.huertohogar_mobile.data.model.Post
import retrofit2.http.GET

interface PostApiService {

    // Endpoint de la API: https://jsonplaceholder.typicode.com/posts
    @GET("posts")
    suspend fun getPosts(): List<Post>
}
