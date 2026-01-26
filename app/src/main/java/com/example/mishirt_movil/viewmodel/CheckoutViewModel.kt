package com.example.mishirt_movil.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mishirt_movil.model.CheckoutUiState
import com.example.mishirt_movil.model.Comuna
import com.example.mishirt_movil.model.DeliveryMethod
import com.example.mishirt_movil.repository.DpaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckoutViewModel : ViewModel() {

    private val dpaRepository = DpaRepository()

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    // Comunas cercanas (regla simple para costo de envío)
    private val nearbyComunas = setOf(
        "Viña del Mar",
        "Valparaíso",
        "Concón",
        "Quilpué",
        "Villa Alemana"
    )

    init {
        fetchComunas()
    }

    fun fetchComunas() {
        viewModelScope.launch {
            val current = _uiState.value
            _uiState.value = current.copy(
                isLoadingComunas = true,
                comunasError = null
            )

            try {
                val list = dpaRepository.getComunas()

                // Logcat para demostrar consumo de API
                Log.d("API_COMUNAS", "Comunas cargadas: ${list.size}. Ej: ${list.firstOrNull()?.nombre}")

                val matched = list.firstOrNull {
                    it.nombre.equals(_uiState.value.city, ignoreCase = true)
                }

                _uiState.value = _uiState.value.copy(
                    comunas = list,
                    selectedComuna = _uiState.value.selectedComuna ?: matched,
                    isLoadingComunas = false
                )
            } catch (e: Exception) {
                Log.e("API_COMUNAS", "Error cargando comunas", e)

                _uiState.value = _uiState.value.copy(
                    isLoadingComunas = false,
                    comunasError = e.message ?: "Error cargando comunas"
                )
            }

            recalc()
        }
    }

    fun setDeliveryMethod(method: DeliveryMethod) {
        val current = _uiState.value
        _uiState.value = current.copy(deliveryMethod = method)
        recalc()
    }

    fun onAddressChange(value: String) {
        val current = _uiState.value
        _uiState.value = current.copy(address = value)
        recalc()
    }

    fun onCityChange(value: String) {
        val current = _uiState.value
        _uiState.value = current.copy(city = value, selectedComuna = null)
        recalc()
    }

    fun onComunaSelected(comuna: Comuna) {
        val current = _uiState.value
        _uiState.value = current.copy(
            selectedComuna = comuna,
            city = comuna.nombre
        )
        recalc()
    }


    fun setInitialAddressIfEmpty(value: String) {
        val current = _uiState.value
        if (current.address.isBlank() && value.isNotBlank()) {
            _uiState.value = current.copy(address = value)
            recalc()
        }
    }

    fun reset() {
        _uiState.value = CheckoutUiState()
    }

    private fun recalc() {
        val current = _uiState.value

        val comunaName = current.selectedComuna?.nombre?.trim().orEmpty()
            .ifBlank { current.city.trim().ifBlank { "" } }

        val shipping = when (current.deliveryMethod) {
            DeliveryMethod.PICKUP -> 0
            DeliveryMethod.DELIVERY -> if (comunaName.isBlank()) 0 else computeShipping(comunaName)
        }

        val canConfirm = when (current.deliveryMethod) {
            DeliveryMethod.PICKUP -> true
            DeliveryMethod.DELIVERY -> current.address.isNotBlank() && comunaName.isNotBlank()
        }

        _uiState.value = current.copy(
            shippingCLP = shipping,
            canConfirm = canConfirm
        )
    }

    private fun computeShipping(comunaName: String): Int {
        val isNearby = nearbyComunas.any { it.equals(comunaName, ignoreCase = true) }
        return if (isNearby) 1990 else 2990
    }
}
