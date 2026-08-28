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

interface Reportable {
    fun mostrarDetalle()
    fun mostrarResumen()
}

class Tienda(private val carrito: CarritoDeCompras) : Reportable {

    override fun mostrarDetalle() {
        println("Detalle")
        var i = 1
        for (p in carrito.getProductos()) {
            println(String.format("%d. %-20s x%d  S/ %8.2f",
                i, p.nombre, p.cantidad, p.precioTotal()))
            i++
        }
    }

    override fun mostrarResumen() {
        val subtotal = carrito.calcularSubtotal()
        val igv = carrito.calcularIGV()
        val total = carrito.calcularTotal()
        val descuento = carrito.calcularDescuento()

        println(String.format("%-22s: %d", "Cantidad de productos", carrito.getProductos().size))
        println(String.format("%-22s: S/ %8.2f", "Subtotal", subtotal))
        println(String.format("%-22s: S/ %8.2f", "IGV (18%)", igv))
        println(String.format("%-22s: S/ %8.2f", "Total a pagar", total))

        val masCaro = carrito.productoMasCaro()
        if (masCaro != null) {
            println("Producto mas caro: ${masCaro.nombre} " +
                    String.format("(S/ %.2f)", masCaro.precio))
        }

        when {
            total > 5000 -> println("Descuento aplicado: 10% por compra mayor a S/ 5000")
            total > 3000 -> println("Descuento aplicado: 5% por compra mayor a S/ 3000")
        }

        val totalConDescuento = total - descuento
        println(String.format("%-22s: S/ %8.2f", "Total con descuento", totalConDescuento))
    }
}