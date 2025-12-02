package com.example.huertohogar_mobile.ui.model

data class CartItem(
    val product: Product,
    var quantity: Int // Cantidad (ej. 1, 2, 5)
) {
    // Calcula el subtotal de este ítem (Precio x Cantidad)
    val total: Double
        get() = product.price * quantity
}