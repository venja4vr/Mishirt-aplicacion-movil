package com.example.mishirt_movil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mishirt_movil.R
import com.example.mishirt_movil.model.ProductUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CatalogViewModel : ViewModel() {

    private val _products = MutableStateFlow(
        listOf(
            ProductUi("Camiseta Chile", "$19.990", R.drawable.shirt_chile),
            ProductUi("Camiseta Japón", "$21.990", R.drawable.shirt_japan),
            ProductUi("Camiseta Argentina", "$20.990", R.drawable.shirt_argentina),
            ProductUi("Camiseta Perú", "$18.990", R.drawable.shirt_peru),
            ProductUi("Camiseta Portugal", "$22.990", R.drawable.shirt_portugal),
            ProductUi("Camiseta Colombia", "$23.990", R.drawable.shirt_colombia)
        )
    )

    val products: StateFlow<List<ProductUi>> = _products.asStateFlow()
}
