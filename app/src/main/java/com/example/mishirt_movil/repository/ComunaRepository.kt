package com.example.mishirt_movil.repository

import com.example.mishirt_movil.model.Comuna

interface ComunaRepository {
    suspend fun getComunas(): List<Comuna>
}
