package com.example.huertohogar_mobile.ui.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 1. Declarar el DataStore con Context
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "preferencias_usuario"
)

 //Clase que maneja la persistencia del estado activo/inactivo del botón usando DataStore.

class EstadoDataStore(private val context: Context) {

    private val ESTADO_ACTIVO = booleanPreferencesKey("modo_activo")

    //Guarda el estado del botón (true/false) en DataStore.

    suspend fun guardarEstado(valor: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ESTADO_ACTIVO] = valor
        }
    }

    // Obtiene el estado del botón como un Flow reactivo.
    fun obtenerEstado(): Flow<Boolean?> {
        return context.dataStore.data
            .map { preferences ->
                preferences[ESTADO_ACTIVO]
            }
    }
}