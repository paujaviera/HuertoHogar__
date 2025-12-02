package com.example.huertohogar_mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar_mobile.ui.model.CartItem
import com.example.huertohogar_mobile.ui.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    // Lista interna de ítems en el carrito
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // Cálculo automático del precio total
    val totalPrice = _cartItems.map { items ->
        items.sumOf { it.total }
    }

    // AÑADIR PRODUCTO
    fun addToCart(product: Product) {
        val currentItems = _cartItems.value.toMutableList()

        // Buscamos si el producto ya está en el carrito
        val existingItem = currentItems.find { it.product.id == product.id }

        if (existingItem != null) {
            // Si ya existe, aumentamos la cantidad
            existingItem.quantity++
        } else {
            // Si no existe, lo agregamos con cantidad 1
            currentItems.add(CartItem(product, 1))
        }

        // Actualizamos el estado para que la UI reaccione
        _cartItems.value = currentItems
    }

    // ELIMINAR PRODUCTO COMPLETO
    fun removeFromCart(item: CartItem) {
        _cartItems.update { currentItems ->
            currentItems.filter { it.product.id != item.product.id }
        }
    }

    // CAMBIAR CANTIDAD (+ o -)
    fun updateQuantity(item: CartItem, increase: Boolean) {
        val currentItems = _cartItems.value.toMutableList()
        val index = currentItems.indexOfFirst { it.product.id == item.product.id }

        if (index != -1) {
            val currentItem = currentItems[index]
            if (increase) {
                // Aumentar
                currentItems[index] = currentItem.copy(quantity = currentItem.quantity + 1)
            } else {
                // Disminuir
                if (currentItem.quantity > 1) {
                    currentItems[index] = currentItem.copy(quantity = currentItem.quantity - 1)
                }
            }
            _cartItems.value = currentItems
        }
    }

    // VACIAR CARRITO (Al comprar)
    fun clearCart() {
        _cartItems.value = emptyList()
    }
}