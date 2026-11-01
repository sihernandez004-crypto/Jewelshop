package com.projectmobile.jewelshop.model

data class PedidoUiState(
    val sabor: Sabor? = null,
    val relleno: Relleno? = null,
    val toppings: Set<Topping> = emptySet(),
    val cantidad: Int=1,
    val nombre: String = "",
    val telefono: String = "",
    val correo: String = "",
    val enviada: Boolean = false
) {
    val nombreValido: Boolean
        get() = nombre.trim().length >= 2

    val telefonoValido: Boolean
        get() =
            telefono.length == 10 &&
                    telefono.all { it.isDigit() }

    val correoValido: Boolean
        get() =
            Regex(
                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
            ).matches(correo.trim())

    val clienteValido: Boolean
        get() =
            nombreValido &&
                    telefonoValido &&
                    correoValido

    val precioUnitario: Int
        get() =
            (sabor?.precio ?: 0) +
                    (relleno?.extra ?: 0) +
                    toppings.sumOf { it.extra }

    val total: Int
        get() =
            precioUnitario * cantidad

    fun esValido(paso: Paso): Boolean {
        return when (paso) {
            Paso.SABOR ->
                sabor != null

            Paso.RELLENO ->
                relleno != null

            Paso.TOPPINGS ->
                toppings.isNotEmpty()

            Paso.CANTIDAD ->
                cantidad in 1..12

            Paso.CLIENTE ->
                clienteValido

            Paso.RESUMEN ->
                sabor != null &&
                        relleno != null &&
                        toppings.isNotEmpty() &&
                        cantidad in 1..12 &&
                        clienteValido

            Paso.EXITO ->
                enviada
        }

    }
}