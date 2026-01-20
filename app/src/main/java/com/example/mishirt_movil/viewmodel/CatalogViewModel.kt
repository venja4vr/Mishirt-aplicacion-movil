package com.example.mishirt_movil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mishirt_movil.model.ProductUi
import com.example.mishirt_movil.repository.ProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CatalogViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<ProductUi>>(ProductsRepository.products)
    val products: StateFlow<List<ProductUi>> = _products.asStateFlow()
}
