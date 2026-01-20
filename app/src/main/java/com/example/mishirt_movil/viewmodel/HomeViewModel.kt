package com.example.mishirt_movil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mishirt_movil.R
import com.example.mishirt_movil.model.CarouselItemUi
import com.example.mishirt_movil.model.HomeUiState
import com.example.mishirt_movil.repository.ProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState(
            appName = "MISHirt",
            carousel = listOf(
                CarouselItemUi(title = "Chile", imageRes = R.drawable.banner_chile, productId = "chile"),
                CarouselItemUi(title = "Japón", imageRes = R.drawable.banner_japan, productId = "japon")
            ),

            featuredProducts = ProductsRepository.getFeatured()
        )
    )

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
}
