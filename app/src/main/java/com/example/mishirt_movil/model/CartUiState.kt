package com.example.mishirt_movil.model

data class CartUiState(
    val items: List<CartItemUi> = emptyList()
)

data class CartItemUi(
    val productId: String,
    val title: String,
    val size: String,
    val unitPriceText: String,
    val unitPriceCLP: Int,
    val imageRes: Int,
    val quantity: Int = 1
)
