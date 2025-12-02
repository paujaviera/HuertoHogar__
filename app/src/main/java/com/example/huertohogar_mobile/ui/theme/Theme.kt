package com.example.huertohogar_mobile.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary, // Verde Oscuro (Botones, Iconos seleccionados)
    onPrimary = LightOnPrimary, // Blanco

    secondary = LightSecondary, // Verde Orgánico Claro (Botones de acceso rápido)
    onSecondary = LightOnSecondary, // Negro

    surface = LightSurface, // Fondo de TopBar y Tarjetas
    background = LightBackground, // Fondo general de la pantalla

    onSurface = LightOnSecondary, // Texto sobre superficies (usando Negro)
    onBackground = LightOnSecondary, // Texto sobre fondo (usando Negro)
    surfaceVariant = Color(0xFFF0F0F0), // Fondo de tarjetas de carrusel (ejemplo)
    onSurfaceVariant = Color.Gray // Texto secundario/descripciones
)

@Composable
fun HuertoHogarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Usamos el tema claro LightColorScheme
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography, // Aplica el sistema de fuentes personalizado
        content = content
    )
}