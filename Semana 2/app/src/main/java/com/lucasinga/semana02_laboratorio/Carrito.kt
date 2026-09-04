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

fun mostrarDetalle(productos: List<Producto>) {
    println("Detalle del carrito:")
    var i = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println("$i. ${p.nombre} x${p.cantidad}  S/ $importe")
    }
}

fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}

fun main() {
    println("Carrito de compras - Tienda Tecsup")

    print("Ingrese su nombre: ")
    val nombreCliente = readLine() ?: "Cliente"
    val carrito = mutableListOf<Producto>()
    println("Cliente: $nombreCliente")
    println()

    print("Cuantos productos desea agregar? ")
    val cantidadProductos = readLine()?.toIntOrNull() ?: 0

    for (i in 1..cantidadProductos) {
        println("Producto $i")
        print("Nombre: ")
        val nombre = readLine() ?: "Producto $i"
        print("Precio: ")
        val precio = readLine()?.toDoubleOrNull() ?: 0.0
        print("Cantidad: ")
        val cant = readLine()?.toIntOrNull() ?: 1
        carrito.add(Producto(nombre, precio, cant))
    }

    for (producto in carrito) {
        println("Producto agregado: ${producto.nombre}")
    }
    println()

    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)

    println("Cantidad de productos: ${carrito.size}")
    println("Subtotal: $subtotal")
    println("IGV (18%): $igv")
    println("Total a pagar: $total")

    mostrarDetalle(carrito)

    val masCaro = carrito.maxByOrNull { it.precio }
    if (masCaro != null) {
        println("Producto mas caro: ${masCaro.nombre} (S/ ${masCaro.precio})")
    }

    val descuento = calcularDescuento(total)
    when {
        total > 5000 -> println("Descuento aplicado: 10% por compra mayor a S/ 5000")
        total > 3000 -> println("Descuento aplicado: 5% por compra mayor a S/ 3000")
    }

    val totalConDescuento = total - descuento
    println("Total con descuento, totalConDescuento")
    println("Gracias por su compra, $nombreCliente!")
}