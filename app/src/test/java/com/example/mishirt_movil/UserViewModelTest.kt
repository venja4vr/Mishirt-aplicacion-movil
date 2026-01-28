package com.example.mishirt_movil.viewmodel

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class UserViewModelTest : StringSpec({

    "onNombreChange debe actualizar el nombre en el uiState" {
        // instanciamos el ViewModel real (sin mock porque es lógica pura)
        val viewModel = UserViewModel()
        val nuevoNombre = "Juan Perez"

        // ejecutamos la acción
        viewModel.onNombreChange(nuevoNombre)

        // verificamos el resultado
        viewModel.uiState.value.nombre shouldBe nuevoNombre
    }

    "crearCuenta deberia marcar cuentaCreada como true si los datos son válidos" {
        val viewModel = UserViewModel()

        // configurar estado válido
        viewModel.onNombreChange("benja")
        viewModel.onCorreoChange("benja@test.com")
        viewModel.onClaveChange("123456")

        viewModel.crearCuenta()

        viewModel.uiState.value.cuentaCreada shouldBe true
    }

    " el metodo crearCuenta no debe crear la cuenta si falta el nombre" {
        val viewModel = UserViewModel()

        viewModel.onNombreChange("") // nombre vacío
        viewModel.onCorreoChange("test@test.com")
        viewModel.onClaveChange("123456")

        viewModel.crearCuenta()

        viewModel.uiState.value.cuentaCreada shouldBe false
    }
})