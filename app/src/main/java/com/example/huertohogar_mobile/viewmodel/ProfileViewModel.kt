package com.example.huertohogar_mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.net.Uri // Necesario para representar la ubicación de la imagen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ViewModel que gestiona el estado de la pantalla de Perfil.
 // Incluye la lógica para almacenar la URI de la imagen seleccionada.

class ProfileViewModel : ViewModel() {

    // 1. ESTADO REACTIVO (URI de la Imagen)
    // Usamos Uri para que el valor pueda ser nulo (cuando no hay imagen seleccionada).
    private val _photoUri = MutableStateFlow<Uri?>(null)

    // VARIABLE PARA GUARDAR EL URI: Expuesto para que la UI lo observe.
    val photoUri: StateFlow<Uri?> = _photoUri.asStateFlow()

    // 2. FUNCIONES DE ACTUALIZACIÓN DE IMAGEN

    // FUNCIÓN PARA ACTUALIZAR desde la Galería/Cámara.
     // Esta función se llama después de que ActivityResultLauncher obtiene la URI.
    fun updatePhotoUri(uri: Uri) {
        viewModelScope.launch {
            // Actualiza el StateFlow, lo que dispara la recomposición de la UI.
            _photoUri.value = uri
        }
    }

    //  Función para simular la eliminación de la foto de perfil
    fun clearPhoto() {
        viewModelScope.launch {
            _photoUri.value = null
        }
    }
}