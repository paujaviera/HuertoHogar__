package com.example.huertohogar_mobile.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogar_mobile.ui.theme.HuertoHogarTheme
import com.example.huertohogar_mobile.viewmodel.AnimationDemoViewModel
import androidx.compose.material3.CircularProgressIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimationDemoScreen(
    viewModel: AnimationDemoViewModel = viewModel()
) {
    // 1. OBSERVACIÓN DEL ESTADO DE CARGA Y PERSISTENCIA (DataStore)
    val isToggledState by viewModel.isToggled.collectAsState(initial = null)

    // 2. OBSERVACIÓN DEL FEEDBACK ANIMADO
    val mostrarMensaje by viewModel.mostrarMensaje.collectAsState()

    // 3. MOSTRAR TEXTO DERIVADO (derivedStateOf)
    val buttonText by remember(isToggledState) {
        derivedStateOf {
            if (isToggledState == true) "Estado Activado (Persistente)" else "Clicks Totales: ${viewModel.count}"
        }
    }

    // 4. ANIMAR EL COLOR
    val targetColor = if (isToggledState == true) MaterialTheme.colorScheme.primary else Color.Red
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 500),
        label = "ButtonColorAnimation"
    )

    Scaffold(topBar = { TopAppBar(title = { Text(text = "Demo de Estados Avanzados") }) }) { innerPadding ->

        // LÓGICA DE CARGA INICIAL
        if (isToggledState == null) {
            // Muestra el indicador de carga mientras DataStore espera
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // UI PRINCIPAL (Se muestra solo cuando el estado está cargado)
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Valor del ViewModel: ${viewModel.count}",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Button(
                    onClick = {
                        viewModel.toggleState()
                        viewModel.increment()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = animatedColor),
                    modifier = Modifier.fillMaxWidth().height(60.dp)
                ) {
                    Text(text = buttonText, color = MaterialTheme.colorScheme.onPrimary)
                }

                Spacer(modifier = Modifier.height(16.dp))

                AnimatedVisibility(
                    visible = mostrarMensaje,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Text(
                        text = "Estado guardado exitosamente",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

// PREVIEW
@Preview(showBackground = true, name = "Demo Avanzada de Compose")
@Composable
fun AnimationDemoScreenPreview() {
    HuertoHogarTheme {
        AnimationDemoScreen()
    }
}