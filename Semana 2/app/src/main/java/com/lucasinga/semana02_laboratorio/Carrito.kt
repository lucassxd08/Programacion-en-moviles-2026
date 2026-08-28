package com.lucasinga.semana02_laboratorio

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun main() {
    println("   Carrito de compras - Tienda Tecsup")

    val nombreCliente = "Lucas Inga"
    val carrito = mutableListOf<Producto>()
    println("Cliente: $nombreCliente")
    println()

    carrito.add(Producto("Audifonos Samsung", 180.0, 1))
    carrito.add(Producto("Monitor", 800.0, 1))
    carrito.add(Producto("Celular", 1200.0, 1))
    carrito.add(Producto("Disco Duro", 250.0, 2))

    for (producto in carrito) {
        println("Producto agregado: ${producto.nombre}")
    }
    println()
}