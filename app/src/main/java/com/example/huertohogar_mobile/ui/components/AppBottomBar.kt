package com.example.huertohogar_mobile.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.huertohogar_mobile.navigation.Screen
import com.example.huertohogar_mobile.viewmodel.MainViewModel

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val screen: Screen
)

@Composable
fun AppBottomBar(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val items = listOf(
        BottomNavItem("Inicio", Icons.Filled.Home, Screen.Home),
        BottomNavItem("Categorías", Icons.Filled.Category, Screen.Categories),
        BottomNavItem("Carrito", Icons.Filled.ShoppingCart, Screen.Cart),
        BottomNavItem("Perfil", Icons.Filled.Person, Screen.Profile)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            val selected = currentRoute == item.screen.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    // Solo navegamos si no estamos ya en esa pantalla
                    if (!selected) {
                        mainViewModel.navigateTo(item.screen)
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    }
}
