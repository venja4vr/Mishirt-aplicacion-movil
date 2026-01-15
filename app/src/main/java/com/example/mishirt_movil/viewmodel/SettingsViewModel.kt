package com.example.mishirt_movil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mishirt_movil.model.SettingsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun toggleTheme() {
        val current = _uiState.value
        _uiState.value = current.copy(isDarkTheme = !current.isDarkTheme)
    }

    fun toggleNotifications() {
        val current = _uiState.value
        _uiState.value = current.copy(notificationsEnabled = !current.notificationsEnabled)
    }
}
