package com.example.mishirt_movil.model

data class UserUiState (
    val nombre: String = "",
    val correo: String = "",
    val direccion: String = "",
    val clave: String = "",
    val cuentaCreada: Boolean = false
)
