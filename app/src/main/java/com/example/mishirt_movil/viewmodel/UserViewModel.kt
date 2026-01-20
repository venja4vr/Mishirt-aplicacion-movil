package com.example.mishirt_movil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mishirt_movil.model.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    fun onNombreChange(value: String) {
        _uiState.value = _uiState.value.copy(nombre = value)
    }

    fun onCorreoChange(value: String) {
        _uiState.value = _uiState.value.copy(correo = value)
    }

    fun onDireccionChange(value: String) {
        _uiState.value = _uiState.value.copy(direccion = value)
    }

    fun onClaveChange(value: String) {
        _uiState.value = _uiState.value.copy(clave = value)
    }

    fun crearCuenta() {
        val s = _uiState.value
        if (s.nombre.isNotBlank() && s.correo.isNotBlank() && s.clave.isNotBlank()) {
            _uiState.value = s.copy(cuentaCreada = true)
        }
    }

    fun setPhoto(uri: String) {
        _uiState.value = _uiState.value.copy(photoUri = uri)
    }

}
