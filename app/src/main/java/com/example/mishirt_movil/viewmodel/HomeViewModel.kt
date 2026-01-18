package com.example.mishirt_movil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mishirt_movil.R
import com.example.mishirt_movil.model.CarouselItemUi
import com.example.mishirt_movil.model.HomeUiState
import com.example.mishirt_movil.model.ProductUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState(
            appName = "MISHirt",
            carousel = listOf(
                CarouselItemUi("Chile", R.drawable.banner_chile),
                CarouselItemUi("Japón", R.drawable.banner_japan)
            ),
            featuredProducts = listOf(
                ProductUi("Camiseta Chile", "$19.990", R.drawable.shirt_chile),
                ProductUi("Camiseta Japón", "$21.990", R.drawable.shirt_japan),
                ProductUi("Camiseta Portugal", "$18.990", R.drawable.shirt_portugal) // temporal
            )
        )
    )

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onProfileClick() {
        // luego: navegación a perfil
    }

    fun onSettingsClick() {
        // luego: navegación a ajustes
    }
}
