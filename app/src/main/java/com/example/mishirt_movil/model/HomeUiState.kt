package com.example.mishirt_movil.model

data class HomeUiState(
    val appName: String = "MISHirt",
    val carousel: List<CarouselItemUi> = emptyList(),
    val featuredProducts: List<ProductUi> = emptyList()
)

data class CarouselItemUi(
    val title: String,
    val imageRes: Int,
    val productId: String
)


data class ProductUi(
    val id: String,
    val title: String,
    val price: String,
    val imageRes: Int,
    val description: String = "",
    val material: String = "",
    val continent: String = "",
    val sizes: List<String> = emptyList(),
    val isFeatured: Boolean = false
)
