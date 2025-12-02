package com.example.huertohogar_mobile.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.huertohogar_mobile.navigation.Screen
import com.example.huertohogar_mobile.ui.model.Product
import com.example.huertohogar_mobile.viewmodel.CartViewModel
import com.example.huertohogar_mobile.viewmodel.HomeViewModel
import com.example.huertohogar_mobile.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    categoryName: String, // Recibe "Frutas", "Verduras", etc.
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel(),
    cartViewModel: CartViewModel,
    mainViewModel: MainViewModel
) {
    val state by homeViewModel.state.collectAsState()
    val context = LocalContext.current

    // 1. FILTRO: Buscamos productos que coincidan con la categoría seleccionada
    val filteredProducts = remember(state.products, categoryName) {
        if (categoryName == "Ver todo" || categoryName == "Ofertas") {
            state.products // Mostrar todos por ahora
        } else {
            // Filtra la lista comparando el campo 'category'
            state.products.filter { it.category == categoryName }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(categoryName) }, // Título dinámico (ej: "Frutas")
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->

        // 2. ESTADO VACÍO (Si no hay productos de esa categoría)
        if (filteredProducts.isEmpty()) {
            Box(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("No encontramos productos aquí 🥕", style = MaterialTheme.typography.titleMedium)
                    Text("Pronto agregaremos más stock.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        } else {
            // 3. LISTA DE PRODUCTOS FILTRADOS
            LazyColumn(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredProducts) { product ->
                    // Usamos la tarjeta definida aquí abajo
                    ProductListRow(
                        product = product,
                        onClick = { mainViewModel.navigateTo(Screen.Detail(product.id)) },
                        onAddToCart = {
                            cartViewModel.addToCart(product)
                            Toast.makeText(context, "Agregado: ${product.name}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

// DEFINICIÓN DE LA TARJETA (Diseño horizontal para listas)
@Composable
fun ProductListRow(
    product: Product,
    onClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Imagen cuadrada izquierda
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.name,
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Crop
            )

            // Información derecha
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Títulos
                Column {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1
                    )
                }

                // Precio y Botón
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$ ${product.price.toInt()}",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    // Botón para agregar al carrito desde la lista
                    IconButton(
                        onClick = onAddToCart,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Agregar",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}