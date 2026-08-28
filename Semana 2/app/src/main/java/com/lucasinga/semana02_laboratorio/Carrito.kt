package com.lucasinga.semana02_laboratorio

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.precio * p.cantidad
    }
    return subtotal
}

fun calcularIGV(subtotal: Double): Double {
    return subtotal * 0.18
}

fun calcularTotal(subtotal: Double, igv: Double): Double {
    return subtotal + igv
}

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

    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)

    println(String.format("%-22s: %d", "Cantidad de productos", carrito.size))
    println(String.format("%-22s: S/ %8.2f", "Subtotal", subtotal))
    println(String.format("%-22s: S/ %8.2f", "IGV (18%)", igv))
    println(String.format("%-22s: S/ %8.2f", "Total a pagar", total))
}