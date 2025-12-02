package com.example.huertohogar_mobile.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.huertohogar_mobile.viewmodel.UsuarioViewModel
import com.example.huertohogar_mobile.ui.theme.HuertoHogarTheme


@Composable
fun ResumenScreen(
    navController: NavController,
    viewModel: UsuarioViewModel = viewModel()
) {
    // 1. Observar el estado (UiState) del ViewModel de forma reactiva
    val estado by viewModel.estado.collectAsState()

    // Acceso directo a los datos del estado
    val datos = estado

    Scaffold(
        topBar = {
            // TopAppBar(title = { Text("Resumen de Datos") })
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            Text(
                text = "Resumen del Registro",
                style = MaterialTheme.typography.headlineMedium
            )

            // visualización de datos //

            Text(text = "Nombre: ${datos.nombre}")
            Text(text = "Correo: ${datos.correo}")
            Text(text = "Dirección: ${datos.direccion}")
            Text(text = "Contraseña: ${"*".repeat(datos.clave?.length ?: 0)}")

            // Términos Aceptados
            val terminosStatus = if (datos.aceptaTerminos) "Aceptados" else "No aceptados"
            Text(text = "Términos: $terminosStatus")

        }
    }
}


@Preview(showBackground = true, name = "Resumen de Datos de Registro")
@Composable
fun ResumenScreenPreview() {
    // Crear NavController simulado
    val navController = rememberNavController()

    // Simular el ViewModel
    val viewModel = viewModel<UsuarioViewModel>()

    HuertoHogarTheme {
        //  Llamar a la función ResumenScreen con los parámetros simulados
        ResumenScreen(navController = navController, viewModel = viewModel)
    }
}