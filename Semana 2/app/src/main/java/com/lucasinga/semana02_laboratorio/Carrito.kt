package com.lucasinga.semana02_laboratorio

abstract class ProductoBase(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
) {
    abstract fun categoria(): String

    fun precioTotal(): Double = precio * cantidad

    override fun toString(): String =
        "$nombre (${categoria()}) - S/ $precio x$cantidad"
}

class ProductoElectronico(
    nombre: String,
    precio: Double,
    cantidad: Int,
    val garantiaMeses: Int
) : ProductoBase(nombre, precio, cantidad) {
    override fun categoria(): String = "Electronico"
}

class ProductoAccesorio(
    nombre: String,
    precio: Double,
    cantidad: Int,
    val material: String
) : ProductoBase(nombre, precio, cantidad) {
    override fun categoria(): String = "Accesorio"
}

class CarritoDeCompras(val nombreCliente: String) {
    private val productos = mutableListOf<ProductoBase>()

    fun agregar(producto: ProductoBase) {
        productos.add(producto)
        println("Producto agregado: ${producto.nombre}")
    }

    fun getProductos(): List<ProductoBase> = productos

    fun calcularSubtotal(): Double = productos.sumOf { it.precioTotal() }

    fun calcularIGV(): Double = calcularSubtotal() * 0.18

    fun calcularTotal(): Double = calcularSubtotal() + calcularIGV()

    fun calcularDescuento(): Double {
        val total = calcularTotal()
        return when {
            total > 5000 -> total * 0.10
            total > 3000 -> total * 0.05
            else -> 0.0
        }
    }

    fun productoMasCaro(): ProductoBase? = productos.maxByOrNull { it.precio }
}