package com.example.huertohogar_mobile.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.example.huertohogar_mobile.navigation.NavigationEvent
import com.example.huertohogar_mobile.navigation.Screen
import com.example.huertohogar_mobile.viewmodel.MainViewModel
import com.example.huertohogar_mobile.viewmodel.UsuarioViewModel
import com.example.huertohogar_mobile.viewmodel.CartViewModel
import com.example.huertohogar_mobile.ui.components.AppBottomBar

// Importamos todas las pantallas
import com.example.huertohogar_mobile.ui.screens.HomeScreen
import com.example.huertohogar_mobile.ui.screens.CartScreen
import com.example.huertohogar_mobile.ui.screens.RegistroScreen
import com.example.huertohogar_mobile.ui.screens.CategoryScreen
import com.example.huertohogar_mobile.ui.screens.ProductListScreen
import com.example.huertohogar_mobile.ui.screens.ResumenScreen
import com.example.huertohogar_mobile.ui.screens.ProfileScreen
import com.example.huertohogar_mobile.ui.screens.SettingsScreen
import com.example.huertohogar_mobile.ui.screens.DetailScreen
import com.example.huertohogar_mobile.ui.screens.OrdersScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AppScreen(windowSizeClass: WindowSizeClass) {

    val navController = rememberNavController()

    val mainViewModel: MainViewModel = viewModel()
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()

    val navigationEvent by mainViewModel.navigationEvents.collectAsState(initial = null)

    LaunchedEffect(navigationEvent) {
        navigationEvent?.let { event ->
            when (val navEvent = event) {
                is NavigationEvent.NavigateTo -> {
                    val routeToGo: String = when (val screen = navEvent.route) {
                        is Screen.Detail -> screen.buildRoute()
                        is Screen.ProductList -> screen.route
                        else -> screen.route
                    }
                    val options = navOptions {
                        navEvent.popUpToRoute?.let { screenToPop ->
                            popUpTo(route = screenToPop.route) { inclusive = navEvent.inclusive }
                        }
                        launchSingleTop = navEvent.singleTop
                    }
                    navController.navigate(route = routeToGo, navOptions = options)
                }
                is NavigationEvent.PopBackstack -> navController.popBackStack()
                is NavigationEvent.NavigateUp -> navController.navigateUp()
            }
        }
    }

    Scaffold(
        bottomBar = {
            AppBottomBar(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController = navController, viewModel = mainViewModel, cartViewModel = cartViewModel)
            }

            composable(Screen.Cart.route) {
                CartScreen(
                    navController = navController,
                    cartViewModel = cartViewModel
                )
            }

            composable(Screen.Registro.route) {
                RegistroScreen(navController = navController, mainViewModel = mainViewModel, usuarioViewModel = usuarioViewModel)
            }
            composable(route = Screen.Categories.route) {
                CategoryScreen(mainViewModel = mainViewModel, cartViewModel = cartViewModel)
            }
            composable(
                route = Screen.ProductList.ROUTE_TEMPLATE,
                arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
            ) { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("categoryName") ?: "Todo"
                ProductListScreen(categoryName = categoryName, navController = navController, cartViewModel = cartViewModel, mainViewModel = mainViewModel)
            }
            composable(Screen.Resumen.route) {
                ResumenScreen(navController = navController, viewModel = usuarioViewModel)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController = navController, viewModel = mainViewModel, usuarioViewModel = usuarioViewModel, profileViewModel = viewModel())
            }
            composable(Screen.Settings.route) {
                SettingsScreen(navController = navController, viewModel = mainViewModel)
            }
            composable(
                route = Screen.Detail.ROUTE_TEMPLATE,
                arguments = listOf(navArgument("itemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
                DetailScreen(itemId = itemId, navController = navController, cartViewModel = cartViewModel)
            }

            // RUTA DE MIS PEDIDOS
            composable(Screen.Orders.route) {
                OrdersScreen(navController = navController)
            }
        }
    }
}