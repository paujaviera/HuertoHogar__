package com.example.huertohogar_mobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import android.net.Uri
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImagenInteligente(
    uri: Uri?,
    size: Dp = 100.dp
) {
    // 1. Definir el Modifier común para el tamaño y la forma circular
    val imageModifier = Modifier
        .size(size)
        // Mostrar la imagen en forma circular
        .clip(CircleShape)
        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)

    // 2. Comprobar si hay una URI válida
    if (uri != null) {
        val painter = rememberAsyncImagePainter(
            model = uri,
            contentScale = ContentScale.Crop // Recortar la imagen para llenar el círculo
        )

        // 3. Mostrar la imagen
        Image(
            painter = painter,
            contentDescription = "Foto de Perfil",
            modifier = imageModifier,
            contentScale = ContentScale.Crop
        )
    } else {
        // 4. Mostrar el icono de perfil (Placeholder)
        Box(
            modifier = imageModifier.background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Ícono de Perfil por defecto",
                modifier = Modifier.fillMaxSize(0.6f), // Tamaño del ícono dentro del círculo
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}