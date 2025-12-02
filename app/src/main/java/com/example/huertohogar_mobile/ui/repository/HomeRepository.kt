package com.example.huertohogar_mobile.ui.repository

import com.example.huertohogar_mobile.R
import com.example.huertohogar_mobile.ui.model.Product

// Repositorio que simula la fuente de datos para la pantalla principal.

class HomeRepository {

    // Lista de productos de prueba (Mocks)
    fun getProducts(): List<Product> {
        return listOf(
            Product(
                id = "FRT001",
                name = "Frutillas Orgánicas",
                description = "Cosecha fresca, ideal para postres.",
                price = 3990.0, // $3.990 CLP
                imageResId = R.drawable.frutillas,
                isOrganic = true,
                category = "Frutas"
            ),
            Product(
                id = "VEG002",
                name = "Tomates Cherry",
                description = "El mejor sabor para tu ensalada.",
                price = 1490.0, // $1.490 CLP
                imageResId = R.drawable.tomates_cherry,
                isOrganic = true,
                category = "Verduras"
            ),
            Product(
                id = "FRT003",
                name = "Manzanas",
                description = "De huerto tradicional, crujientes y dulces.",
                price = 1250.0, // $1.250 CLP
                imageResId = R.drawable.manzanas,
                isOrganic = false,
                category = "Frutas"
            ),
            Product(
                id = "VEG004",
                name = "Zanahorias",
                description = "Perfectas para jugos y decoración de platillos.",
                price = 890.0, // $890 CLP
                imageResId = R.drawable.zanahorias,
                isOrganic = true,
                category = "Verduras"
            ),
            Product(
                id = "VEG005",
                name = "Rúcula",
                description = "Hojas frescas lavadas, listas para servir.",
                price = 990.0,
                imageResId = R.drawable.rucula,
                isOrganic = true,
                category = "Verduras"
            ),
            Product(
                id = "SEC001",
                name = "Quinoa Real",
                description = "Superalimento rico en proteínas y fibra.",
                price = 4500.0,
                imageResId = R.drawable.quinoa,
                isOrganic = true,
                category = "Secos"
            ),
            Product(
                id = "SEC002",
                name = "Nueces Mariposa",
                description = "Selección premium, sin cáscara.",
                price = 6990.0,
                imageResId = R.drawable.nueces,
                isOrganic = true,
                category = "Secos"
            ),
            Product(
                id = "ALA001",
                name = "Queso Fresco",
                description = "Textura suave, elaborado artesanalmente.",
                price = 3200.0,
                imageResId = R.drawable.queso,
                isOrganic = false,
                category = "Alacena"
            ),
            // NUEVO: Leche
            Product(
                id = "ALA002",
                name = "Leche Entera Natural",
                description = "Botella de vidrio 1L, pasteurizada.",
                price = 1450.0,
                imageResId = R.drawable.leche,
                isOrganic = true,
                category = "Alacena"
        )
        )
    }
}