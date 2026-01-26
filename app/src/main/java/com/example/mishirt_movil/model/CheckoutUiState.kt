package com.example.mishirt_movil.model

enum class DeliveryMethod {
    PICKUP,
    DELIVERY
}

data class CheckoutUiState(
    val deliveryMethod: DeliveryMethod = DeliveryMethod.PICKUP,
    val address: String = "",
    val city: String = "",

    // API comunas
    val comunas: List<Comuna> = emptyList(),
    val selectedComuna: Comuna? = null,
    val isLoadingComunas: Boolean = false,
    val comunasError: String? = null,

    val shippingCLP: Int = 0,
    val canConfirm: Boolean = true
)
