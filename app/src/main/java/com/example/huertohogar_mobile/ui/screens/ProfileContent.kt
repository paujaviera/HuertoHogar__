package com.example.huertohogar_mobile.ui.screens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.huertohogar_mobile.navigation.Screen
import com.example.huertohogar_mobile.ui.components.ImagenInteligente
import com.example.huertohogar_mobile.viewmodel.MainViewModel
import com.example.huertohogar_mobile.viewmodel.ProfileViewModel
import com.example.huertohogar_mobile.viewmodel.UsuarioViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProfileContent(
    navController: NavController,
    mainViewModel: MainViewModel,
    usuarioViewModel: UsuarioViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel()
) {
    val photoUri by profileViewModel.photoUri.collectAsState()
    val estadoUsuario by usuarioViewModel.estado.collectAsState()
    val context = LocalContext.current

    val userName = estadoUsuario.nombre.ifEmpty { "Usuario HuertoHogar" }
    val userEmail = estadoUsuario.correo.ifEmpty { "invitado@huertohogar.cl" }

    // --- VARIABLES DE ESTADO ---
    var showEditDialog by remember { mutableStateOf(false) }
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }

    // --- CÁMARA ---
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempPhotoUri != null) {
            profileViewModel.updatePhotoUri(tempPhotoUri!!)
            Toast.makeText(context, "Foto actualizada!", Toast.LENGTH_SHORT).show()
        }
    }

    fun takePhoto() {
        try {
            val uri = createImageFile(context)
            tempPhotoUri = uri
            cameraLauncher.launch(uri)
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // --- UI PRINCIPAL ---
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))

        // FOTO CLICABLE
        Box(
            modifier = Modifier.clickable { takePhoto() },
            contentAlignment = Alignment.BottomEnd
        ) {
            ImagenInteligente(uri = photoUri, size = 120.dp)
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Cambiar foto",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.background(Color.White, shape = MaterialTheme.shapes.small).padding(4.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(text = userName, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text(text = userEmail, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

        Spacer(Modifier.height(4.dp))
        TextButton(onClick = { takePhoto() }) { Text("Cambiar foto") }

        Divider(modifier = Modifier.padding(vertical = 24.dp))

        // BOTÓN EDITAR
        ProfileMenuItem(
            icon = Icons.Filled.Edit,
            title = "Editar Mis Datos",
            onClick = { showEditDialog = true }
        )

        // DATOS ACTUALES (Dirección y Teléfono)
        ProfileMenuItem(
            icon = Icons.Filled.LocationOn,
            title = "Dirección: ${estadoUsuario.direccion.ifEmpty { "Sin definir" }}",
            onClick = { showEditDialog = true }
        )

        ProfileMenuItem(
            icon = Icons.Filled.Phone,
            title = "Teléfono: ${estadoUsuario.telefono.ifEmpty { "Sin definir" }}",
            onClick = { showEditDialog = true }
        )

        // BOTÓN MIS PEDIDOS
        ProfileMenuItem(
            icon = Icons.Filled.ShoppingBag,
            title = "Mis Pedidos",
            onClick = { mainViewModel.navigateTo(Screen.Orders) }
        )

        Spacer(modifier = Modifier.weight(1f))
        Divider(modifier = Modifier.padding(bottom = 16.dp))

        // CERRAR SESIÓN
        ProfileMenuItem(
            icon = Icons.Filled.ExitToApp,
            title = "Cerrar Sesión",
            contentColor = MaterialTheme.colorScheme.error,
            onClick = { mainViewModel.setLoggedIn(false) }
        )
        Spacer(Modifier.height(16.dp))
    }

    // DIÁLOGO DE EDICIÓN
    if (showEditDialog) {
        EditarDatosDialog(
            nombreActual = estadoUsuario.nombre,
            direccionActual = estadoUsuario.direccion,
            telefonoActual = estadoUsuario.telefono,
            onDismiss = { showEditDialog = false },
            onConfirm = { nuevoNombre, nuevaDir, nuevoTel ->
                usuarioViewModel.actualizarDatos(nuevoNombre, nuevaDir, nuevoTel)
                showEditDialog = false
                Toast.makeText(context, "Datos actualizados", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
fun EditarDatosDialog(
    nombreActual: String,
    direccionActual: String,
    telefonoActual: String,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    var nombre by remember { mutableStateOf(nombreActual) }
    var direccion by remember { mutableStateOf(direccionActual) }
    var telefono by remember { mutableStateOf(telefonoActual) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Actualizar Datos") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
                OutlinedTextField(value = direccion, onValueChange = { direccion = it }, label = { Text("Dirección") })
                OutlinedTextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
            }
        },
        confirmButton = { Button(onClick = { onConfirm(nombre, direccion, telefono) }) { Text("Guardar") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = title, tint = contentColor, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(16.dp))
        Text(text = title, style = MaterialTheme.typography.bodyLarge, color = contentColor, modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

fun createImageFile(context: Context): Uri {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(imageFileName, ".jpg", context.cacheDir)
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", image)
}