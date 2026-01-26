package com.example.mishirt_movil.data.remote

import retrofit2.http.GET

interface DpaApiService {
    @GET("comunas-regiones.json")
    suspend fun getRegionesComunas(): RegionesComunasDto
}
