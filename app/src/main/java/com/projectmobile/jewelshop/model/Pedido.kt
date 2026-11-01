package com.projectmobile.jewelshop.model

enum class Sabor(
    val nombre: String,
    val precio: Int
) {
    VAINILLA (nombre = "Vainilla Mexicana", precio = 40),
    CHOCOLATE (nombre = "Chocolate", precio = 40),
    ARANDANOS (nombre = "Arándanos", precio =50)
}

enum class Topping(
    val nombre: String,
    val extra: Int
) {
    SIN_TOPPINGS(nombre = "Sin toppings", extra = 0),
    CHISPAS(nombre = "Chispas de chocolate", extra = 6),
    CONFETI(nombre = "Chispas de colores", extra = 5),
    CREMA(nombre = "Crema batida", extra = 7),
    FRUTA(nombre = "Fruta", extra = 8)
}


enum class Relleno(
    val nombre: String,
    val extra: Int
) {
    SIN_RELLENO(nombre = "Sin relleno", extra = 0),
    CHOCOLATE(nombre = "Crema de chocolate", extra = 10),
    VAINILLA(nombre = "Crema de vainilla", extra = 8),
    FRUTOS_ROJOS(nombre = "Mermelada de frutos rojos", extra = 9)
}

enum class Paso(
    val ruta: String,
    val titulo: String
) {
    SABOR(ruta = "sabor", titulo = "Elige el sabor"),
    RELLENO(ruta = "relleno", titulo = "Elige el relleno"),
    TOPPINGS(ruta = "toppings", titulo = "Elige los toppings"),
    CANTIDAD(ruta = "cantidad", titulo = "Indica la cantidad"),
    CLIENTE(ruta = "cliente", titulo = "Tus datos"),
    RESUMEN(ruta = "resumen", titulo = "Resumen de pedido"),
    EXITO(ruta = "exito", titulo = "¡Orden enviada!")
}