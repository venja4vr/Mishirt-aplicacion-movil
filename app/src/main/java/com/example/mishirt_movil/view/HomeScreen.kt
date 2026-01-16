package com.example.mishirt_movil.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mishirt_movil.model.HomeUiState

@Composable
fun HomeScreen(
    state: HomeUiState,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // contenido del home
        }
    }
}
