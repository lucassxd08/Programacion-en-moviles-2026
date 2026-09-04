package com.lucasinga.semana02_laboratorio

data class Vehiculo(
    val placa: String,
    val tipo: String,
    val horas: Int,
    val esFrecuente: Boolean
)

fun tarifaBase(tipo: String): Double {
    return when (tipo) {
        "moto" -> 2.0
        "auto" -> 4.0
        "camioneta" -> 10.0
        else -> 0.0
    }
}

fun calcularRecargoPorcentaje(horas: Int): Int {
    return when {
        horas <= 2 -> 0
        horas <= 3 -> 20
        horas <= 5 -> 0
        else -> 50
    }
}

fun calcularImporte(tipo: String, horas: Int, esFrecuente: Boolean): Double {
    val tarifa = tarifaBase(tipo)
    val importeNormal = tarifa * horas

    val recargo = when {
        horas <= 2 -> 0.0
        horas <= 3 -> importeNormal * 0.20
        horas <= 5 -> 0.0
        else -> (horas - 5) * tarifa * 0.50
    }

    val subtotal = importeNormal + recargo
    val descuento = if (esFrecuente) subtotal * 0.10 else 0.0
    return subtotal - descuento
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
        val importe = calcularImporte(v.tipo, v.horas, v.esFrecuente)
        totalRecaudado += importe

        println("${v.placa}  -  ${v.tipo}  -  ${v.horas}h  -  S/$tarifa  -  $recargoPct%  -  S/$importe")
    }

    println("Total: S/ $totalRecaudado")
}

fun main() {
    println("Sistema de Estacionamiento")
    println()
    println("Tarifas base:")
    println("  Moto:      S/ 2 por hora")
    println("  Auto:      S/ 4 por hora")
    println("  Camioneta: S/ 10 por hora")
    println()

    print("Cuantos vehiculos va a procesar? ")
    val cantidad = readLine()?.toIntOrNull() ?: 0

    val vehiculos = mutableListOf<Vehiculo>()

    for (i in 1..cantidad) {
        println()
        println("--- Vehiculo $i ---")
        print("Placa: ")
        val placa = readLine() ?: "SIN-PLACA"

        print("Tipo (moto / auto / camioneta): ")
        val tipo = readLine()?.lowercase() ?: "auto"

        print("Horas de permanencia (minimo 1): ")
        var horas = readLine()?.toIntOrNull() ?: 1
        if (horas < 1) {
            println("Minimo 1 hora. Se registra 1 hora.")
            horas = 1
        }

        print("Es cliente frecuente? (s/n): ")
        val esFrecuente = readLine()?.lowercase() == "s"

        vehiculos.add(Vehiculo(placa, tipo, horas, esFrecuente))
    }
    mostrarReporte(vehiculos)
}