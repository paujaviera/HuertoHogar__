package com.example.huertohogar_mobile.navigation

sealed class Screen(val route: String) {

    // Rutas simples (sin argumentos)
    data object Home : Screen(route = "home_page")
    data object Settings : Screen(route = "settings_page")
    data object Profile : Screen(route = "profile_page")
    data object Registro : Screen(route = "registro_page")
    data object Resumen : Screen(route = "resumen_page")
    data object Categories : Screen(route = "categories_page")
    data object Cart : Screen(route = "cart_page")

    // Ruta de MIS PEDIDOS
    data object Orders : Screen(route = "orders_page")

    // Ruta de DETALLE de producto (recibe ID)
    data class Detail(val itemId: String) : Screen(route = "detail_page/$itemId") {
        fun buildRoute(): String = route
        companion object {
            const val ROUTE_TEMPLATE = "detail_page/{itemId}"
        }
    }

    // LISTA DE PRODUCTOS (Recibe nombre de categoría)
    data class ProductList(val categoryName: String) : Screen(route = "product_list/$categoryName") {
        companion object {
            const val ROUTE_TEMPLATE = "product_list/{categoryName}"
        }
    }
}