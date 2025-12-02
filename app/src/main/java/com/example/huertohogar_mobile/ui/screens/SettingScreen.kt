package com.example.huertohogar_mobile.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.huertohogar_mobile.navigation.Screen.*
import com.example.huertohogar_mobile.viewmodel.MainViewModel
import com.example.huertohogar_mobile.ui.theme.HuertoHogarTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Configuración") })
        }
    ) { innerPadding ->
        // Estructura visual centralizada
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp), // Marco interno general
            verticalArrangement = Arrangement.Center, // Centrar elementos verticalmente
            horizontalAlignment = Alignment.CenterHorizontally // Centrar elementos horizontalmente
        ) {
            // Título o texto principal
            Text(text = "Pantalla de Configuración (Settings)")

            Spacer(modifier = Modifier.height(24.dp)) // Espacio vertical

            // Botón para volver al Home
            Button(
                onClick = {
                    //  ACCIÓN MVVM: Emitir evento de navegación al ViewModel
                    viewModel.navigateTo(Home)
                }
            ) {
                Text(text = "Volver al Inicio")
            }

            Spacer(modifier = Modifier.height(16.dp)) // Más espacio

            // Botón para ir al Perfil
            OutlinedButton(
                onClick = {
                    //  ACCIÓN MVVM: Emitir evento para ir a perfil
                    viewModel.navigateTo(Profile)
                }
            ) {
                Text(text = "Ir a Perfil")
            }
        }
    }
}


@Preview(showBackground = true, name = "Diseño Settings Final")
@Composable
fun SettingsScreenPreview() {
    val navController = rememberNavController()

    HuertoHogarTheme {

        SettingsScreen(navController = navController)
    }
}