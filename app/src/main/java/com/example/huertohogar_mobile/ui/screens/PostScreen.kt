package com.example.huertohogar_mobile.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogar_mobile.viewmodel.PostViewModel

private val sampleTitles = listOf(
    "Cómo elegir frutas y verduras de temporada",
    "Consejos para regar tus plantas en verano",
    "Ideas para armar una canasta saludable",
    "Beneficios de consumir productos orgánicos",
    "Cómo conservar mejor tus verduras en el refrigerador"
)

private val sampleBodies = listOf(
    "Comprar productos de temporada te ayuda a ahorrar y disfrutar de más sabor. Fíjate en el origen y prefiere siempre productores locales.",
    "Riega temprano en la mañana o al atardecer para evitar la evaporación. Revisa la humedad de la tierra antes de volver a regar.",
    "Combina hojas verdes, frutas frescas y frutos secos. Así logras una canasta equilibrada, colorida y llena de nutrientes.",
    "Los productos orgánicos reducen el uso de químicos y cuidan el suelo. Son una excelente opción para una alimentación más consciente.",
    "Guarda las verduras en recipientes limpios y secos. Evita el exceso de humedad para que duren más tiempo frescas."
)

@Composable
fun PostScreen(
    viewModel: PostViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Título de la sección con estilo mejorado
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Text(
                text = "Tips",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.errorMessage != null -> {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Error: ${state.errorMessage}",
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            else -> {
                // Mapeo usando tus textos originales
                val mappedPosts = state.posts
                    .take(5)
                    .mapIndexed { index, post ->
                        val title = sampleTitles.getOrNull(index)
                            ?: "Noticia del huerto #${index + 1}"
                        val body = sampleBodies.getOrNull(index)
                            ?: "Muy pronto tendremos más novedades de Huerto Hogar."
                        post.copy(title = title, body = body)
                    }

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    mappedPosts.forEach { post ->
                        NewsCard(title = post.title, body = post.body)
                    }
                }
            }
        }
    }
}

@Composable
fun NewsCard(title: String, body: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        // Fondo Blanco
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        // Sombra suave
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        // Borde verde
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Encabezado con ícono
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Spa,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary // Título en verde
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }
    }
}