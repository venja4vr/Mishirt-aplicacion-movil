package com.example.mishirt_movil.model

data class HomeUiState(
    val appName: String = "MISHirt",
    val carousel: List<CarouselItemUi> = emptyList(),
    val featuredProducts: List<ProductUi> = emptyList()
)

data class CarouselItemUi(
    val title: String,
    val imageRes: Int
)

data class ProductUi(
    val title: String,
    val price: String,
    val imageRes: Int
)

