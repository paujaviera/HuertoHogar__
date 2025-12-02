package com.example.huertohogar_mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogar_mobile.navigation.Screen
import com.example.huertohogar_mobile.viewmodel.MainViewModel
import com.example.huertohogar_mobile.viewmodel.CartViewModel

// 1. Modelo de datos
data class CategoryItemData(
    val name: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    mainViewModel: MainViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {
    // 2. OBSERVAMOS EL ESTADO DEL CARRITO
    val cartItems by cartViewModel.cartItems.collectAsState()
    // Calculamos la cantidad total de productos
    val itemCount = cartItems.sumOf { it.quantity }

    val categories = listOf(
        CategoryItemData("Ofertas", Icons.Outlined.LocalOffer),
        CategoryItemData("Frutas", Icons.Outlined.Eco),
        CategoryItemData("Verduras", Icons.Outlined.Spa),
        CategoryItemData("Secos", Icons.Outlined.Grain),
        CategoryItemData("Alacena", Icons.Outlined.Kitchen),
        CategoryItemData("Ver todo", Icons.Outlined.GridView)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Categorías",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                },
                actions = {
                    IconButton(onClick = { /* Buscar */ }) {
                        Icon(Icons.Outlined.Search, contentDescription = "Buscar")
                    }
                    IconButton(onClick = { /* Notificaciones */ }) {
                        Icon(Icons.Outlined.Notifications, contentDescription = "Alertas")
                    }

                    // ICONO DEL CARRITO
                    IconButton(onClick = { mainViewModel.navigateTo(Screen.Cart) }) {
                        BadgedBox(
                            badge = {
                                // Solo mostramos la burbuja roja si hay cosas (> 0)
                                if (itemCount > 0) {
                                    Badge { Text(itemCount.toString()) }
                                }
                            }
                        ) {
                            Icon(Icons.Outlined.ShoppingCart, contentDescription = "Carrito")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->

        // 3. Lista Vertical
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(categories) { category ->
                CategoryRowItem(
                    category = category,
                    onClick = {
                        mainViewModel.navigateTo(Screen.ProductList(categoryName = category.name))
                    }
                )
            }
        }
    }
}

// 4. Diseño de cada fila
@Composable
fun CategoryRowItem(
    category: CategoryItemData,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = null,
                tint = Color.DarkGray,
                modifier = Modifier.size(26.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 17.sp),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.LightGray
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 66.dp),
            thickness = 1.dp,
            color = Color.LightGray.copy(alpha = 0.2f)
        )
    }
}