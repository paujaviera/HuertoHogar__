package com.example.huertohogar_mobile.ui.utils

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

//Utilidad para calcular el tamaño de pantalla actual (compacto, mediano o expandido).
 //Esto permite adaptar el layout según el dispositivo (celular, tablet, etc.)

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun obtenerWindowSizeClass(): WindowSizeClass {
    val context = LocalContext.current

    // El 'context as Activity' es necesario porque calculateWindowSizeClass lo requiere.
    return calculateWindowSizeClass(context as Activity)
}