package com.example.mishirt_movil.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mishirt_movil.model.CheckoutUiState
import com.example.mishirt_movil.model.Comuna
import com.example.mishirt_movil.model.DeliveryMethod
import com.example.mishirt_movil.repository.ComunaRepository
import com.example.mishirt_movil.repository.DpaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckoutViewModel @JvmOverloads constructor(
    private val comunaRepository: ComunaRepository = DpaRepository(),
    private val autoFetch: Boolean = true
) : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    private val nearbyComunas = setOf(
        "Viña del Mar",
        "Valparaíso",
        "Concón",
        "Quilpué",
        "Villa Alemana"
    )

    init {
        if (autoFetch) fetchComunas()
    }

    fun fetchComunas() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoadingComunas = true,
                comunasError = null
            )

            try {
                val list = comunaRepository.getComunas()
                runCatching { Log.d("API_COMUNAS", "Comunas cargadas: ${list.size}") }

                val matched = list.firstOrNull {
                    it.nombre.equals(_uiState.value.city, ignoreCase = true)
                }

                _uiState.value = _uiState.value.copy(
                    comunas = list,
                    selectedComuna = _uiState.value.selectedComuna ?: matched,
                    isLoadingComunas = false
                )
            } catch (e: Exception) {
                runCatching { Log.e("API_COMUNAS", "Error cargando comunas", e) }

                _uiState.value = _uiState.value.copy(
                    isLoadingComunas = false,
                    comunasError = e.message ?: "Error cargando comunas"
                )
            }

            recalc()
        }
    }

    fun setDeliveryMethod(method: DeliveryMethod) {
        _uiState.value = _uiState.value.copy(deliveryMethod = method)
        recalc()
    }

    fun onAddressChange(value: String) {
        _uiState.value = _uiState.value.copy(address = value)
        recalc()
    }

    fun onComunaSelected(comuna: Comuna) {
        _uiState.value = _uiState.value.copy(
            selectedComuna = comuna,
            city = comuna.nombre
        )
        recalc()
    }

    // ESTE ES EL MÉTODO QUE TU MainActivity ESTÁ LLAMANDO
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
