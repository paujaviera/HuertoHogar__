package com.example.huertohogar_mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar_mobile.data.model.Post
import com.example.huertohogar_mobile.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class PostUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val errorMessage: String? = null
)

class PostViewModel(
    private val repository: PostRepository = PostRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostUiState(isLoading = true))
    val uiState: StateFlow<PostUiState> = _uiState

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = PostUiState(isLoading = true)

            try {
                val result = repository.getPosts()
                _uiState.value = PostUiState(
                    isLoading = false,
                    posts = result,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = PostUiState(
                    isLoading = false,
                    posts = emptyList(),
                    errorMessage = e.localizedMessage ?: "Error al cargar datos"
                )
            }
        }
    }
}
