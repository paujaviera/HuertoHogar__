package com.example.huertohogar_mobile.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.huertohogar_mobile.viewmodel.MainViewModel
import com.example.huertohogar_mobile.viewmodel.UsuarioViewModel
import com.example.huertohogar_mobile.ui.theme.HuertoHogarTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    val estado by usuarioViewModel.estado.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Registro de Usuario") })
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Ingresa tus datos",
                style = MaterialTheme.typography.headlineSmall
            )

            // 2. CAMPOS DEL FORMULARIO
            // Campo NOMBRE (Con animación de error)
            OutlinedTextField(
                value = estado.nombre,
                onValueChange = usuarioViewModel::onNombreChange,
                label = { Text("Nombre y apellidos") },
                isError = estado.errores.nombre != null,
                supportingText = {
                    AnimatedErrorText(error = estado.errores.nombre)
                },
                modifier = Modifier.fillMaxWidth()
            )

            // CAMPO TELÉFONO(Con animación de error)
            OutlinedTextField(
                value = estado.telefono,
                onValueChange = usuarioViewModel::onTelefonoChange,
                label = { Text("Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = estado.errores.telefono != null,
                supportingText = {
                    AnimatedErrorText(error = estado.errores.telefono)
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo CORREO ELECTRÓNICO (Con animación de error)
            OutlinedTextField(
                value = estado.correo,
                onValueChange = usuarioViewModel::onCorreoChange,
                label = { Text("Correo electrónico") },
                isError = estado.errores.correo != null,
                supportingText = {
                    AnimatedErrorText(error = estado.errores.correo)
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo CLAVE (Contraseña con animación de error)
            OutlinedTextField(
                value = estado.clave,
                onValueChange = usuarioViewModel::onClaveChange,
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = estado.errores.clave != null,
                supportingText = {
                    AnimatedErrorText(error = estado.errores.clave)
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo DIRECCIÓN (Con animación de error)
            OutlinedTextField(
                value = estado.direccion,
                onValueChange = usuarioViewModel::onDireccionChange,
                label = { Text("Dirección") },
                isError = estado.errores.direccion != null,
                supportingText = {
                    AnimatedErrorText(error = estado.errores.direccion)
                },
                modifier = Modifier.fillMaxWidth()
            )

            // CHECKBOX (Aceptar Términos)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = estado.aceptaTerminos,
                    onCheckedChange = usuarioViewModel::onAceptarTerminosChange
                )
                Spacer(Modifier.width(8.dp))
                Text(text = "Acepto los términos y condiciones")
            }

            // 4. BOTÓN ENVIAR (Con animación de carga)
            AnimatedSubmitButton(
                isLoading = estado.isLoading,
                onClick = {
                    if (usuarioViewModel.validarFormulario()) {
                        usuarioViewModel.onRegistroSubmit(mainViewModel)
                    }
                }
            )
        }
    }
}

@Composable
fun AnimatedSubmitButton(isLoading: Boolean, onClick: () -> Unit) {
    val scale by animateFloatAsState(
        targetValue = if (isLoading) 0.95f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "Scale_Animation"
    )
    val buttonColor = if (isLoading)
        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f)
    else MaterialTheme.colorScheme.primary

    Button(
        onClick = onClick,
        enabled = !isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 3.dp
            )
        } else {
            Text(text = "Registrar")
        }
    }
}

@Composable
fun AnimatedErrorText(error: String?) {
    AnimatedVisibility(
        visible = error != null,
        enter = expandVertically(animationSpec = tween(200)),
        exit = shrinkVertically(animationSpec = tween(200))
    ) {
        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}


@Preview(showBackground = true, name = "Formulario de Registro")
@Composable
fun RegistroScreenPreview() {
    val navController = rememberNavController()
    val usuarioViewModel = viewModel<UsuarioViewModel>()
    val mainViewModel = viewModel<MainViewModel>()

    HuertoHogarTheme {
        RegistroScreen(
            navController = navController,
            mainViewModel = mainViewModel,
            usuarioViewModel = usuarioViewModel
        )
    }
}