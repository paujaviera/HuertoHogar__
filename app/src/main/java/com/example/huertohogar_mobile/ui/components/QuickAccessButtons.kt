package com.example.huertohogar_mobile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding

@Composable
fun QuickAccessButtons() {
    // Implementación simple de una fila con botones de acceso rápido
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(onClick = {}) { Text("Ofertas") }
        Button(onClick = {}) { Text("Frutas") }
        Button(onClick = {}) { Text("Vegetales") }
    }
}

