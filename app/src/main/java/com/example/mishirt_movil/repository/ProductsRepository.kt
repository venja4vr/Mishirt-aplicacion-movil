package com.example.mishirt_movil.repository

import com.example.mishirt_movil.R
import com.example.mishirt_movil.model.ProductUi

object ProductsRepository {

    // Lista única para Home, Catálogo y Detalle
    val products: List<ProductUi> = listOf(
        ProductUi(
            id = "chile",
            title = "Camiseta Chile",
            price = "$19.990",
            imageRes = R.drawable.shirt_chile,
            description = "Camiseta de la selección de Chile 2025",
            material = "Poliéster deportivo (secado rápido)",
            continent = "America",
            sizes = listOf("S", "M", "L", "XL"),
            isFeatured = true
        ),
        ProductUi(
            id = "japon",
            title = "Camiseta Japón",
            price = "$21.990",
            imageRes = R.drawable.shirt_japan,
            description = "Camiseta de la selección de Japón 2025.",
            material = "Poliéster deportivo (transpirable)",
            continent = "Asia",
            sizes = listOf("S", "M", "L", "XL"),
            isFeatured = true
        ),
        ProductUi(
            id = "argentina",
            title = "Camiseta Argentina",
            price = "$20.990",
            imageRes = R.drawable.shirt_argentina,
            description = "Camiseta de la selección de Argentina 2025.",
            material = "Poliéster deportivo",
            continent = "America",
            sizes = listOf("S", "M", "L", "XL"),
            isFeatured = false
        ),
        ProductUi(
            id = "peru",
            title = "Camiseta Perú",
            price = "$18.990",
            imageRes = R.drawable.shirt_peru,
            description = "Camiseta de la selección de Perú 2025.",
            material = "Poliéster deportivo",
            continent = "America",
            sizes = listOf("S", "M", "L", "XL"),
            isFeatured = false
        ),
        ProductUi(
            id = "portugal",
            title = "Camiseta Portugal",
            price = "$22.990",
            imageRes = R.drawable.shirt_portugal,
            description = "Camiseta de la selección de Portugal 2025.",
            material = "Poliéster deportivo",
            continent = "Europa",
            sizes = listOf("S", "M", "L", "XL"),
            isFeatured = false
        ),
        ProductUi(
            id = "marruecos",
            title = "Camiseta Marruecos",
            price = "$22.990",
            imageRes = R.drawable.shirt_marruecos,
            description = "Camiseta de la selección de Marruecos 2025.",
            material = "Poliéster deportivo",
            continent = "Africa",
            sizes = listOf("S", "M", "L", "XL"),
            isFeatured = true
        ),
        ProductUi(
            id = "colombia",
            title = "Camiseta Colombia",
            price = "$23.990",
            imageRes = R.drawable.shirt_colombia,
            description = "Camiseta de la selección de Colombia 2025.",
            material = "Poliéster deportivo",
            continent = "America",
            sizes = listOf("S", "M", "L", "XL"),
            isFeatured = false
        )



    )

    fun getFeatured(): List<ProductUi> = products.filter { it.isFeatured }

    fun getById(id: String): ProductUi? = products.firstOrNull { it.id == id }
}
