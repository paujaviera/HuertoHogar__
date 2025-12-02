package com.example.huertohogar_mobile.ui.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserDataStore(private val context: Context) {

    companion object {
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_PASS = stringPreferencesKey("user_pass")
        val USER_ADDR = stringPreferencesKey("user_addr")
        val USER_PHONE = stringPreferencesKey("user_phone")
    }

    // Guardar (o Actualizar) Usuario Completo
    suspend fun saveUser(
        nombre: String,
        correo: String,
        clave: String,
        direccion: String, // Nuevo
        telefono: String   // Nuevo
    ) {
        context.userDataStore.edit { prefs ->
            prefs[USER_NAME] = nombre
            prefs[USER_EMAIL] = correo
            prefs[USER_PASS] = clave
            prefs[USER_ADDR] = direccion
            prefs[USER_PHONE] = telefono
        }
    }

    // Obtener datos
    val userData: Flow<UserData> = context.userDataStore.data.map { prefs ->
        UserData(
            nombre = prefs[USER_NAME] ?: "",
            correo = prefs[USER_EMAIL] ?: "",
            clave = prefs[USER_PASS] ?: "",
            direccion = prefs[USER_ADDR] ?: "",
            telefono = prefs[USER_PHONE] ?: ""
        )
    }

    suspend fun clearUser() {
        context.userDataStore.edit { prefs -> prefs.clear() }
    }
}

// Modelo de datos actualizado
data class UserData(
    val nombre: String,
    val correo: String,
    val clave: String,
    val direccion: String,
    val telefono: String
)