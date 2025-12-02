package com.example.huertohogar_mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar_mobile.ui.model.Product
import com.example.huertohogar_mobile.ui.repository.HomeRepository
import com.example.huertohogar_mobile.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// 1. Clase que define el ESTADO completo de la Home Screen
data class HomeState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

//  ViewModel que gestiona la lógica y el estado de la Home Screen.
 // Expone la lista de productos y maneja la carga inicial.
class HomeViewModel(
    // Inyección simple del repositorio
    private val repository: HomeRepository = HomeRepository()
) : ViewModel() {

    // 2. Estado Mutable Privado
    private val _state = MutableStateFlow(HomeState())

    // 3. Estado Público Reactivo (StateFlow)
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadProducts()
    }

    // Carga la lista de productos del repositorio
    private fun loadProducts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                // Simulación de carga de datos (desde HomeRepository)
                val loadedProducts = repository.getProducts()

                _state.update { currentState ->
                    currentState.copy(
                        products = loadedProducts,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                // Manejo de errores simulado
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error al cargar productos: ${e.message}"
                    )
                }
            }
        }
    }

    // Función para navegar al detalle (llamada desde HomeScreen)
    fun onProductClick(productId: String, mainViewModel: MainViewModel) {
        // Emite el evento de navegación al Router principal
        mainViewModel.navigateTo(Screen.Detail(itemId = productId))
    }

    // Función para simular el evento de búsqueda (para el componente SearchBarHogar
    fun onSearchQueryChange(query: String) {
        // En un futuro implementaremos la lógica de filtrado aquí (ej. products.filter { ... })
        println("Búsqueda iniciada para: $query")
    }
}