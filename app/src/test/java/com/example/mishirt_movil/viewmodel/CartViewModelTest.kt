package com.example.mishirt_movil.viewmodel

import com.example.mishirt_movil.model.ProductUi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CartViewModelTest {

    private fun product(
        id: String,
        title: String,
        price: String,
        imageRes: Int = 0
    ): ProductUi {
        return ProductUi(
            id = id,
            title = title,
            price = price,
            imageRes = imageRes
        )
    }

    @Test
    fun add_firstTime_createsNewItem_quantity1_andParsesPrice() {
        val vm = CartViewModel()
        val p = product(id = "chile", title = "Camiseta Chile", price = "$19.990")

        vm.add(p, "M")

        val items = vm.uiState.value.items
        assertEquals(1, items.size)
        assertEquals("chile", items[0].productId)
        assertEquals("M", items[0].size)
        assertEquals(1, items[0].quantity)

        // Verifica parsePriceCLP indirectamente (desde add)
        assertEquals(19990, items[0].unitPriceCLP)
    }

    @Test
    fun add_sameProductSameSize_twice_incrementsQuantity() {
        val vm = CartViewModel()
        val p = product(id = "japon", title = "Camiseta Japón", price = "$21.990")

        vm.add(p, "L")
        vm.add(p, "L")

        val item = vm.uiState.value.items.first()
        assertEquals(1, vm.uiState.value.items.size)
        assertEquals(2, item.quantity)
    }

    @Test
    fun add_sameProductDifferentSize_createsTwoLines() {
        val vm = CartViewModel()
        val p = product(id = "japon", title = "Camiseta Japón", price = "$21.990")

        vm.add(p, "M")
        vm.add(p, "XL")

        val items = vm.uiState.value.items
        assertEquals(2, items.size)
        assertTrue(items.any { it.productId == "japon" && it.size == "M" })
        assertTrue(items.any { it.productId == "japon" && it.size == "XL" })
    }

    @Test
    fun increase_existingLine_incrementsQuantity() {
        val vm = CartViewModel()
        val p = product(id = "peru", title = "Camiseta Perú", price = "$18.990")

        vm.add(p, "S")
        vm.increase("peru", "S")

        val item = vm.uiState.value.items.first()
        assertEquals(2, item.quantity)
    }

    @Test
    fun decrease_whenQuantityBecomesZero_removesLine() {
        val vm = CartViewModel()
        val p = product(id = "portugal", title = "Camiseta Portugal", price = "$22.990")

        vm.add(p, "M")          // quantity = 1
        vm.decrease("portugal", "M") // debe eliminar

        assertTrue(vm.uiState.value.items.isEmpty())
    }

    @Test
    fun remove_deletesOnlyThatLine() {
        val vm = CartViewModel()
        val p = product(id = "colombia", title = "Camiseta Colombia", price = "$23.990")

        vm.add(p, "M")
        vm.add(p, "L")

        vm.remove("colombia", "M")

        val items = vm.uiState.value.items
        assertEquals(1, items.size)
        assertEquals("L", items[0].size)
    }

    @Test
    fun clear_emptiesCart() {
        val vm = CartViewModel()
        val p = product(id = "marruecos", title = "Camiseta Marruecos", price = "$22.990")

        vm.add(p, "XL")
        vm.clear()

        assertTrue(vm.uiState.value.items.isEmpty())
    }

    @Test
    fun totalCLP_sumsPriceTimesQuantity_correctly() {
        val vm = CartViewModel()
        val p1 = product(id = "a", title = "A", price = "$1.000")
        val p2 = product(id = "b", title = "B", price = "$2.000")

        vm.add(p1, "M") // 1000 x 1
        vm.add(p2, "M") // 2000 x 1
        vm.add(p2, "M") // 2000 x 2

        val total = vm.totalCLP()
        assertEquals(1000 + (2000 * 2), total)
    }

    @Test
    fun formatCLP_formatsWithDotsAndDollarSign() {
        val vm = CartViewModel()

        assertEquals("$1.990", vm.formatCLP(1990))
        assertEquals("$19.990", vm.formatCLP(19990))
        assertEquals("$1.000.000", vm.formatCLP(1_000_000))
    }
}