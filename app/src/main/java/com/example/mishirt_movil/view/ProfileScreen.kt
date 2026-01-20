package com.example.mishirt_movil.view

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.mishirt_movil.model.UserUiState
import com.example.mishirt_movil.ui.theme.MossGreen
import java.io.File

@Composable
fun ProfileScreen(
    state: UserUiState,
    onSetPhoto: (String) -> Unit
) {
    val context = LocalContext.current
    var showPicker by remember { mutableStateOf(false) }

    // uri temporal para la cámara
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    // launcher: galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) onSetPhoto(uri.toString())
    }

    // launcher: tomar foto (cámara)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempCameraUri != null) {
            onSetPhoto(tempCameraUri.toString())
        }
    }

    // launcher: permiso cámara
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = createTempImageUri(context)
            tempCameraUri = uri
            cameraLauncher.launch(uri)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // foto circular
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(Color(0xFFE9E9E9))
                .border(2.dp, Color(0xFFCCCCCC), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (state.photoUri != null) {
                AsyncImage(
                    model = state.photoUri,
                    contentDescription = "foto perfil",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text("Foto.", color = Color(0xFF666666))
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = { showPicker = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = MossGreen,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Cambiar foto")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // card datos
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text("datos", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(12.dp))

                ProfileField("Nombre:", state.nombre)
                Spacer(modifier = Modifier.height(10.dp))
                ProfileField("Correo:", state.correo)
                Spacer(modifier = Modifier.height(10.dp))
                ProfileField("Dirección:", state.direccion)
            }
        }
    }

    // diálogo para elegir recurso nativo
    if (showPicker) {
        AlertDialog(
            onDismissRequest = { showPicker = false },
            title = { Text("cambiar foto") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                    Text(
                        text = "Usar cámara",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showPicker = false
                                cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                            }
                            .padding(10.dp)
                    )

                    Text(
                        text = "Elegir desde galería",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showPicker = false
                                galleryLauncher.launch("image/*")
                            }
                            .padding(10.dp)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showPicker = false }) { Text("cerrar") }
            }
        )
    }
}

@Composable
private fun ProfileField(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = Color(0xFF666666))
        Text(text = if (value.isBlank()) "-" else value, style = MaterialTheme.typography.bodyLarge)
    }
}

// crea un archivo temporal y devuelve su uri vía FileProvider
private fun createTempImageUri(context: Context): Uri {
    val file = File.createTempFile("profile_", ".jpg", context.cacheDir)
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
}
