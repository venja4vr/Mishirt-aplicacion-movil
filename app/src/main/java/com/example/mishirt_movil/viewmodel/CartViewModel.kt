package com.example.mishirt_movil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mishirt_movil.model.CartItemUi
import com.example.mishirt_movil.model.CartUiState
import com.example.mishirt_movil.model.ProductUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    fun add(product: ProductUi, size: String) {
        val key = buildKey(product.id, size)
        val current = _uiState.value.items.toMutableList()

        val index = current.indexOfFirst { buildKey(it.productId, it.size) == key }

        if (index >= 0) {
            val item = current[index]
            current[index] = item.copy(quantity = item.quantity + 1)
        } else {
            val unitPrice = parsePriceCLP(product.price)
            current.add(
                CartItemUi(
                    productId = product.id,
                    title = product.title,
                    size = size,
                    unitPriceText = product.price,
                    unitPriceCLP = unitPrice,
                    imageRes = product.imageRes,
                    quantity = 1
                )
            )
        }

        _uiState.value = _uiState.value.copy(items = current)
    }

    fun increase(productId: String, size: String) {
        val key = buildKey(productId, size)
        val current = _uiState.value.items.toMutableList()
        val index = current.indexOfFirst { buildKey(it.productId, it.size) == key }
        if (index >= 0) {
            val item = current[index]
            current[index] = item.copy(quantity = item.quantity + 1)
            _uiState.value = _uiState.value.copy(items = current)
        }
    }

    fun decrease(productId: String, size: String) {
        val key = buildKey(productId, size)
        val current = _uiState.value.items.toMutableList()
        val index = current.indexOfFirst { buildKey(it.productId, it.size) == key }
        if (index >= 0) {
            val item = current[index]
            if (item.quantity <= 1) {
                current.removeAt(index)
            } else {
                current[index] = item.copy(quantity = item.quantity - 1)
            }
            _uiState.value = _uiState.value.copy(items = current)
        }
    }

    fun remove(productId: String, size: String) {
        val key = buildKey(productId, size)
        val current = _uiState.value.items.filterNot { buildKey(it.productId, it.size) == key }
        _uiState.value = _uiState.value.copy(items = current)
    }

    fun clear() {
        _uiState.value = CartUiState()
    }

    fun totalCLP(items: List<CartItemUi> = _uiState.value.items): Int {
        return items.sumOf { it.unitPriceCLP * it.quantity }
    }

    fun formatCLP(value: Int): String {
        val s = value.toString()
        val sb = StringBuilder()
        var count = 0
        for (i in s.length - 1 downTo 0) {
            sb.append(s[i])
            count++
            if (count == 3 && i != 0) {
                sb.append('.')
                count = 0
            }
        }
        return "$" + sb.reverse().toString()
    }

    private fun buildKey(productId: String, size: String): String {
        return "$productId|$size"
    }

    private fun parsePriceCLP(text: String): Int {
        // "$19.990" -> 19990
        val digits = text
            .replace("$", "")
            .replace(".", "")
            .replace(" ", "")
            .trim()

        return digits.toIntOrNull() ?: 0
    }
}
