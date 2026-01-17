package com.example.mishirt_movil.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mishirt_movil.model.UserUiState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import com.example.mishirt_movil.ui.theme.MossGreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    state: UserUiState,
    onNombreChange: (String) -> Unit,
    onCorreoChange: (String) -> Unit,
    onDireccionChange: (String) -> Unit,
    onClaveChange: (String) -> Unit,
    onCrearCuenta: () -> Unit
) {
    // contenedor centrado
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 420.dp) // limita el ancho
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "crear cuenta")

                OutlinedTextField(
                    value = state.nombre,
                    onValueChange = onNombreChange,
                    label = { Text("nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = state.correo,
                    onValueChange = onCorreoChange,
                    label = { Text("correo") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = state.direccion,
                    onValueChange = onDireccionChange,
                    label = { Text("dirección") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = state.clave,
                    onValueChange = onClaveChange,
                    label = { Text("clave") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = onCrearCuenta,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MossGreen,
                    contentColor = Color.White
                )
                ) {
                    Text("crear")
                }
            }
        }
    }
}
