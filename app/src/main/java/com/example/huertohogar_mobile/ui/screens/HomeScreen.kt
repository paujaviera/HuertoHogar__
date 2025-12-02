package com.example.huertohogar_mobile.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogar_mobile.navigation.Screen.*
import com.example.huertohogar_mobile.viewmodel.MainViewModel
import com.example.huertohogar_mobile.viewmodel.HomeViewModel
import com.example.huertohogar_mobile.ui.model.Product
import com.example.huertohogar_mobile.R
import com.example.huertohogar_mobile.ui.theme.HuertoHogarTheme
import com.example.huertohogar_mobile.ui.components.SearchBarHogar
import com.example.huertohogar_mobile.viewmodel.CartViewModel
import com.example.huertohogar_mobile.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel(),
    cartViewModel: CartViewModel
) {
    val state by homeViewModel.state.collectAsState()

    val context = LocalContext.current


    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo Huerto Hogar",
                            modifier = Modifier.height(90.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {

            // 1. BARRA DE BÚSQUEDA
            item { SearchBarHogar() }

            // 2. BANNER
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth().height(180.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.banner),
                            contentDescription = "Banner",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            // ATAJOS RÁPIDOS
            item {
                QuickCategoryShortcuts(mainViewModel = viewModel)
                Divider(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp))
            }

            // MÁS VENDIDOS
            item {
                Text(
                    text = "Más vendidos",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
            }

            // CARRUSEL DE PRODUCTOS
            item {
                if (state.isLoading) {
                    CircularProgressIndicator(Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally).padding(24.dp))
                } else if (state.errorMessage != null) {
                    Text("Error: ${state.errorMessage}", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
                } else {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.products) { product ->
                            ProductCardHorizontal(
                                product = product,
                                onClick = { viewModel.navigateTo(Detail(itemId = product.id)) },
                                onAddToCart = {
                                    // A. Agregar al carrito (Lógica)
                                    cartViewModel.addToCart(product)

                                    // B. Mostrar mensaje visual (Toast)
                                    Toast.makeText(context, "Agregado: ${product.name}", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                }
            }
            // SECCIÓN API EXTERNA (Retrofit + PostViewModel)
            item {
               PostScreen()
            }

            item { Spacer(Modifier.height(16.dp)) }
        }
    }
}

data class QuickLinkData(val title: String, val iconResId: Int, val categoryRoute: String)

@Composable
fun QuickCategoryShortcuts(mainViewModel: MainViewModel) {
    val organicCategories = listOf(
        QuickLinkData("Ofertas", R.drawable.ofertas, "Ofertas"),
        QuickLinkData("Frutas", R.drawable.frutas, "Frutas"),
        QuickLinkData("Verduras", R.drawable.verduras, "Verduras"),
        QuickLinkData("Secos", R.drawable.secos, "Secos"),
        QuickLinkData("Otros", R.drawable.otros, "Alacena")
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(organicCategories) { item ->
            QuickLinkItem(title = item.title, iconResId = item.iconResId, onClick = {
                mainViewModel.navigateTo(Screen.ProductList(categoryName = item.categoryRoute))
            })
        }
    }
}

@Composable
fun QuickLinkItem(title: String, iconResId: Int, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(65.dp).clickable(onClick = onClick)) {
        Surface(shape = CircleShape, color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f), modifier = Modifier.size(56.dp)) {
            Icon(painter = painterResource(id = iconResId), contentDescription = title, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(4.dp).fillMaxSize())
        }
        Spacer(Modifier.height(4.dp))
        Text(text = title, style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.Center, maxLines = 2, minLines = 2)
    }
}

@Composable
fun ProductCardHorizontal(
    product: Product,
    onClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier.width(180.dp).height(240.dp).clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.name,
                modifier = Modifier.fillMaxWidth().height(160.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(product.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(product.description, style = MaterialTheme.typography.bodySmall, maxLines = 1, overflow = TextOverflow.Ellipsis, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "$ ${product.price.toInt()} CLP", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1f))
                    IconButton(onClick = onAddToCart, modifier = Modifier.size(24.dp)) {
                        Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Agregar", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}