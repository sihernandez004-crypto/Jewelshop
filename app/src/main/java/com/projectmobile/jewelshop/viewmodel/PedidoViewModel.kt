package com.projectmobile.jewelshop.viewmodel

import androidx.lifecycle.ViewModel
import com.projectmobile.jewelshop.model.Paso
import com.projectmobile.jewelshop.model.PedidoUiState
import com.projectmobile.jewelshop.model.Relleno
import com.projectmobile.jewelshop.model.Sabor
import com.projectmobile.jewelshop.model.Topping
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PedidoViewModel : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            PedidoUiState()
        )
    val uiState: StateFlow<PedidoUiState> =
        _uiState.asStateFlow()

    fun seleccionarSabor(sabor: Sabor) {
        _uiState.update {
            it.copy(
                sabor = sabor
            )
        }
    }

    fun seleccionarRelleno(
        relleno: Relleno
    ) {
        _uiState.update {
            it.copy(
                relleno = relleno
            )
        }
    }

    fun alternarTopping(
        topping: Topping
    ) {
        _uiState.update { estado ->
            val actuales =
                estado.toppings

            val nuevos =
                when {
                    topping == Topping.SIN_TOPPINGS -> {
                        setOf(
                            Topping.SIN_TOPPINGS
                        )
                    }

                    topping in actuales -> {
                        actuales - topping
                    } else -> {
                        (actuales - Topping.SIN_TOPPINGS) + topping
                    }
                }
            estado.copy(
                toppings = nuevos
            )
        }
    }

    fun cambiarCantidad(
        nuevaCantidad: Int
    ) {
        _uiState.update {
            it.copy(
                cantidad =
                    nuevaCantidad.coerceIn(
                        1,
                        12
                    )
            )
        }
    }

    fun cambiarNombre(
        nombre: String
    ) {
        _uiState.update {
            it.copy(
                nombre = nombre
            )
        }
    }

    fun cambiarTelefono(
        telefono: String
    ) {
        _uiState.update {
            it.copy(
                telefono =
                    telefono
                        .filter(Char::isDigit)
                        .take(10)
            )
        }
    }

    fun cambiarCorreo(
        correo: String
    ) {
        _uiState.update {
            it.copy(
                correo = correo
            )
        }
    }

    fun confirmarPedido() {
        _uiState.update { estado ->

            if (
                estado.esValido(
                    paso = Paso.RESUMEN
                )
            ) {
                estado.copy(
                    enviada = true
                )
            } else {
                estado
            }
        }
    }

    fun reiniciar() {
        _uiState.value =
            PedidoUiState()
    }
}