package com.example.mishirt_movil.viewmodel

import com.example.mishirt_movil.model.Comuna
import com.example.mishirt_movil.model.DeliveryMethod
import com.example.mishirt_movil.repository.ComunaRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CheckoutViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private class FakeComunaRepository(
        private val result: Result<List<Comuna>>
    ) : ComunaRepository {
        override suspend fun getComunas(): List<Comuna> = result.getOrThrow()
    }

    @Test
    fun pickup_shipping0_y_canConfirmTrue() {
        val vm = CheckoutViewModel(
            comunaRepository = FakeComunaRepository(Result.success(emptyList())),
            autoFetch = false
        )

        vm.setDeliveryMethod(DeliveryMethod.PICKUP)

        assertEquals(0, vm.uiState.value.shippingCLP)
        assertTrue(vm.uiState.value.canConfirm)
    }

    @Test
    fun delivery_sinComuna_noPermiteConfirmar() {
        val vm = CheckoutViewModel(
            comunaRepository = FakeComunaRepository(Result.success(emptyList())),
            autoFetch = false
        )

        vm.setDeliveryMethod(DeliveryMethod.DELIVERY)
        vm.onAddressChange("Aviador Acevedo")

        assertFalse(vm.uiState.value.canConfirm)
        assertEquals(0, vm.uiState.value.shippingCLP)
    }

    @Test
    fun delivery_conDireccionYComuna_permiteConfirmar_y_envio2990() {
        val vm = CheckoutViewModel(
            comunaRepository = FakeComunaRepository(Result.success(emptyList())),
            autoFetch = false
        )

        vm.setDeliveryMethod(DeliveryMethod.DELIVERY)
        vm.onAddressChange("Aviador Acevedo")
        vm.onComunaSelected(Comuna(nombre = "Cerro Navia"))

        assertTrue(vm.uiState.value.canConfirm)
        assertEquals(2990, vm.uiState.value.shippingCLP)
    }

    @Test
    fun delivery_conComunaCercana_envio1990() {
        val vm = CheckoutViewModel(
            comunaRepository = FakeComunaRepository(Result.success(emptyList())),
            autoFetch = false
        )

        vm.setDeliveryMethod(DeliveryMethod.DELIVERY)
        vm.onAddressChange("Aviador Acevedo")
        vm.onComunaSelected(Comuna(nombre = "Viña del Mar"))

        assertTrue(vm.uiState.value.canConfirm)
        assertEquals(1990, vm.uiState.value.shippingCLP)
    }

    @Test
    fun fetchComunas_success_llenaLista_y_quitaLoading() {
        runTest {
            val comunas = listOf(
                Comuna(nombre = "Viña del Mar"),
                Comuna(nombre = "Valparaíso")
            )

            val vm = CheckoutViewModel(
                comunaRepository = FakeComunaRepository(Result.success(comunas)),
                autoFetch = false
            )

            vm.fetchComunas()
            advanceUntilIdle()

            assertFalse(vm.uiState.value.isLoadingComunas)
            assertNull(vm.uiState.value.comunasError)
            assertEquals(2, vm.uiState.value.comunas.size)
        }
    }

    @Test
    fun fetchComunas_error_seteaComunasError() {
        runTest {
            val vm = CheckoutViewModel(
                comunaRepository = FakeComunaRepository(Result.failure(RuntimeException("fallo"))),
                autoFetch = false
            )

            vm.fetchComunas()
            advanceUntilIdle()

            assertFalse(vm.uiState.value.isLoadingComunas)
            assertEquals("fallo", vm.uiState.value.comunasError)
        }
    }
}
