package com.example.huertohogar_mobile.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.huertohogar_mobile.R


// 1. DEFINICIÓN DE FUENTES

val InterFontFamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_bold, FontWeight.Bold)
)

val MontserratFontFamily = FontFamily(
    Font(R.font.montserrat_bold, FontWeight.Bold)
)


// 2. APLICACIÓN DE LA TIPOGRAFÍA A LOS ESTILOS DE MATERIAL 3

val AppTypography: Typography
    get() = Typography().copy(

        // BODY LARGE: Texto principal de interfaz y párrafos
        bodyLarge = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),

        // HEADLINE SMALL: Usado para títulos de sección grandes (ej. "Más vendidos")
        headlineSmall = TextStyle(
            fontFamily = MontserratFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 32.sp
        ),

        // TITLE LARGE: Títulos de pantalla
        titleLarge = TextStyle(
            fontFamily = MontserratFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 28.sp
        ),

        // TITLE SMALL: Títulos dentro de tarjetas (ej. Nombre del Producto)
        titleSmall = TextStyle(
            fontFamily = MontserratFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        ),
    )
