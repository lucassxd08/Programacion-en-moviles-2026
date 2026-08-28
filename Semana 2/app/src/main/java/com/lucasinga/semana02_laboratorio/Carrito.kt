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