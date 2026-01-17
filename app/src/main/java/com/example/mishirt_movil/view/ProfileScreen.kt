package com.example.mishirt_movil.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mishirt_movil.model.UserUiState

@Composable
fun ProfileScreen(
    state: UserUiState
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("perfil")
        Text("nombre: ${state.nombre}")
        Text("correo: ${state.correo}")
        Text("dirección: ${state.direccion}")
    }
}
