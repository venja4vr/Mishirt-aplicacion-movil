package com.example.mishirt_movil.repository

import com.example.mishirt_movil.data.remote.DpaRetrofitInstance
import com.example.mishirt_movil.model.Comuna

class DpaRepository {

    suspend fun getComunas(): List<Comuna> {
        val response = DpaRetrofitInstance.api.getRegionesComunas()

        return response.regiones
            .flatMap { it.comunas }
            .map { nombre -> Comuna(nombre = nombre) }
            .distinctBy { it.nombre.lowercase() }
            .sortedBy { it.nombre.lowercase() }
    }
}
