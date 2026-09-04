package com.lucasinga.semana02_laboratorio

data class Vehiculo(
    val placa: String,
    val tipo: String,
    val horas: Int,
)

fun tarifaBase(tipo: String): Double {
    return when (tipo) {
        "moto" -> 2.0
        "auto" -> 4.0
        "camioneta" -> 10.0
        "trailer" -> 20.0
        else -> 0.0
    }
}

fun calcularRecargoPorcentaje(horas: Int): Int {
    return when {
        horas <= 2 -> 0
        horas <= 5 -> 20
        horas <= 10 -> 40
        else -> 50
    }
}

fun calcularImporte(tipo: String, horas: Int): Double {
    val tarifa = tarifaBase(tipo)
    val importeNormal = tarifa * horas
    return when {
        horas <= 2 -> importeNormal
        horas <= 5 -> importeNormal * 1.20
        horas <= 10 -> importeNormal * 1.40
        else -> importeNormal * 1.50
    }
}

fun mostrarReporte(vehiculos: List<Vehiculo>) {
    println()
    println("Resumen")
    println("Placa       Tipo        Horas  Tarifa  Recargo  Importe")
    println("----------------------------------------------------------")

    var totalRecaudado = 0.0

    for (v in vehiculos) {
        val tarifa = tarifaBase(v.tipo)
        val recargoPct = calcularRecargoPorcentaje(v.horas)
        val importe = calcularImporte(v.tipo, v.horas)
        totalRecaudado += importe

        println("${v.placa}  -  ${v.tipo}  -  ${v.horas}h  -  S/$tarifa  -  $recargoPct%  -  S/$importe")
    }

    println("Subtotal: S/ $totalRecaudado")

    val descuentoExtra = if (totalRecaudado > 500) totalRecaudado * 0.20 else 0.0
    val subtotalConDescuento = totalRecaudado - descuentoExtra

    if (totalRecaudado > 500) {
        println("Descuento extra 20% (supera S/ 500): S/ $descuentoExtra")
        println("Subtotal con descuento: S/ $subtotalConDescuento")
    }

    val igv = subtotalConDescuento * 0.18
    val totalFinal = subtotalConDescuento + igv
    println("IGV (18%): S/ $igv")
    println("Total final: S/ $totalFinal")
}

fun main() {
    println("Sistema de Estacionamiento")
    println()
    println("Tarifas base:")
    println("  Moto:      S/ 2 por hora")
    println("  Auto:      S/ 4 por hora")
    println("  Camioneta: S/ 10 por hora")
    println("  Trailer: S/ 20 por hora")
    println()

    print("Cuantos vehiculos va a procesar? ")
    val cantidad = readLine()?.toIntOrNull() ?: 0

    val vehiculos = mutableListOf<Vehiculo>()

    for (i in 1..cantidad) {
        println()
        println("--- Vehiculo $i ---")
        print("Placa: ")
        val placa = readLine() ?: "SIN-PLACA"

        print("Tipo (moto / auto / camioneta / trailer): ")
        val tipo = readLine()?.lowercase() ?: "auto"

        print("Horas de permanencia (minimo 1): ")
        var horas = readLine()?.toIntOrNull() ?: 1
        if (horas < 1) {
            println("Minimo 1 hora. Se registra 1 hora.")
            horas = 1
        }

        vehiculos.add(Vehiculo(placa, tipo, horas))
    }
    mostrarReporte(vehiculos)
}