package com.example.mishirt_movil.data.remote

import com.google.gson.annotations.SerializedName
import com.example.mishirt_movil.model.Comuna

data class ComunaDto(
    @SerializedName("codigo") val codigo: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("codigo_padre") val codigoPadre: String? = null,
    @SerializedName("lat") val lat: String? = null,
    @SerializedName("lng") val lng: String? = null,
    @SerializedName("url") val url: String? = null
)

fun ComunaDto.toDomain(): Comuna = Comuna(
    codigo = codigo,
    nombre = nombre
)
