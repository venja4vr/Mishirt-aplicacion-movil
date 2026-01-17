package com.example.mishirt_movil.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mishirt_movil.model.SettingsUiState

@Composable
fun SettingsScreen(
    state: SettingsUiState,
    onBack: () -> Unit,
    onToggleTheme: () -> Unit,
    onToggleNotifications: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "volver"
                )
            }
            Text(
                    text = "AJUSTES"
            )
        }

        Divider()

        // tema
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Tema")
                Text(if (state.isDarkTheme) "Oscuro" else "Claro")
            }
            Switch(
                checked = state.isDarkTheme,
                onCheckedChange = { onToggleTheme() }
            )
        }

        Divider()

        // notificaciones
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Notificaciones")
                Text(if (state.notificationsEnabled) "Activadas" else "Desactivadas")
            }
            Switch(
                checked = state.notificationsEnabled,
                onCheckedChange = { onToggleNotifications() }
            )
        }
    }
}
