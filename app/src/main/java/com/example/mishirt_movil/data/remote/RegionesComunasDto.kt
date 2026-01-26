package com.example.mishirt_movil.data.remote

data class RegionesComunasDto(
    val regiones: List<RegionDto>
)

data class RegionDto(
    val region: String,
    val comunas: List<String>
)
