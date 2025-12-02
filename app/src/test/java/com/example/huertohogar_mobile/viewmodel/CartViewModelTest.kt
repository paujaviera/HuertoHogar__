package com.example.huertohogar_mobile.viewmodel

import com.example.huertohogar_mobile.ui.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    private lateinit var viewModel: CartViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    // Producto de prueba
    private val productoPrueba = Product(
        id = "1",
        name = "Manzana",
        description = "Roja",
        price = 1000.0,
        imageResId = 0,
        category = "Frutas"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CartViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addToCart agrega un producto nuevo correctamente`() = runTest {
        viewModel.addToCart(productoPrueba)

        val items = viewModel.cartItems.value
        assertEquals(1, items.size)
        assertEquals("Manzana", items[0].product.name)
        assertEquals(1, items[0].quantity)
    }

    @Test
    fun `addToCart incrementa la cantidad si el producto ya existe`() = runTest {
        // Agregamos dos veces el mismo producto
        viewModel.addToCart(productoPrueba)
        viewModel.addToCart(productoPrueba)

        val items = viewModel.cartItems.value
        assertEquals(1, items.size) // Sigue siendo un solo ítem en la lista
        assertEquals(2, items[0].quantity) // Pero la cantidad es 2
    }

    @Test
    fun `removeFromCart elimina el producto del carrito`() = runTest {
        viewModel.addToCart(productoPrueba)
        val itemEnCarrito = viewModel.cartItems.value[0]

        viewModel.removeFromCart(itemEnCarrito)

        assertTrue(viewModel.cartItems.value.isEmpty())
    }

    @Test
    fun `updateQuantity disminuye la cantidad correctamente`() = runTest {
        // Agregamos 2 manzanas
        viewModel.addToCart(productoPrueba)
        viewModel.addToCart(productoPrueba)
        val itemEnCarrito = viewModel.cartItems.value[0]

        // Disminuimos 1
        viewModel.updateQuantity(itemEnCarrito, increase = false)

        assertEquals(1, viewModel.cartItems.value[0].quantity)
    }

    @Test
    fun `clearCart vacia el carrito completamente`() = runTest {
        viewModel.addToCart(productoPrueba)
        viewModel.clearCart()

        assertTrue(viewModel.cartItems.value.isEmpty())
    }
}