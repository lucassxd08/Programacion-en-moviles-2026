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