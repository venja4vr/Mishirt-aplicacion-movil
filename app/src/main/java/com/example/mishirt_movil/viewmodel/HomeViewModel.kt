package com.example.mishirt_movil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mishirt_movil.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onProfileClick() {
        // boton al perfil
    }

    fun onSettingsClick() {
        // boton para ajustes
    }
}
