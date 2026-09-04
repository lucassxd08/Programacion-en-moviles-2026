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

fun calcularImporte(tipo: String, horas: Int, esFrecuente: Boolean): Double {
    val tarifa = tarifaBase(tipo)
    val importeNormal = tarifa * horas
    val importeConRecargo = when {
        horas <= 2 -> importeNormal
        horas <= 5 -> importeNormal * 1.20
        horas <= 10 -> importeNormal * 1.40
        else -> importeNormal * 1.50
    }
    val descuentoFrecuente = if (esFrecuente) importeConRecargo * 0.10 else 0.0
    return importeConRecargo - descuentoFrecuente
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
        val frecuenteTag = if (v.esFrecuente) " [Frecuente]" else ""
        println("${v.placa}  -  ${v.tipo}  -  ${v.horas}h  -  S/$tarifa  -  $recargoPct%  -  S/$importe$frecuenteTag")
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
    println("  Trailer:   S/ 20 por hora")
    println()

    print("Ingrese el aforo del estacionamiento: ")
    val aforo = readLine()?.toIntOrNull() ?: 0

    val vehiculos = mutableListOf<Vehiculo>()
    val visitasPorPlaca = mutableMapOf<String, Int>()
    var contador = 0

    while (true) {
        println()
        println("=== MENU ===")
        println("1. Registrar vehiculo ($contador/$aforo espacios ocupados)")
        println("2. Ver reporte")
        println("3. Salir")
        print("Seleccione una opcion: ")

        when (readLine()?.trim()) {
            "1" -> {
                if (contador >= aforo) {
                    println("Aforo completo. No se pueden registrar mas vehiculos.")
                } else {
                    println()
                    println("--- Vehiculo ${contador + 1} de $aforo ---")
                    println("Espacios disponibles: ${aforo - contador}")

                    print("Placa: ")
                    val placa = readLine() ?: "SIN-PLACA"

                    print("Tipo (moto / auto / camioneta / trailer): ")
                    val tipo = readLine()?.lowercase() ?: "auto"

                    print("Horas de permanencia (minimo 1, maximo 24): ")
                    var horas = readLine()?.toIntOrNull() ?: 1
                    if (horas < 1) {
                        println("Minimo 1 hora. Se registra 1 hora.")
                        horas = 1
                    } else if (horas > 24) {
                        println("Maximo 24 horas. Se registra 24 horas.")
                        horas = 24
                    }

                    val visitas = (visitasPorPlaca[placa] ?: 0) + 1
                    visitasPorPlaca[placa] = visitas
                    val esFrecuente = visitas >= 3

                    if (esFrecuente) {
                        println("Cliente frecuente detectado ($visitas visitas). Descuento del 10% aplicado.")
                    } else {
                        println("Visitas registradas para esta placa: $visitas de 3 para descuento frecuente.")
                    }

                    vehiculos.add(Vehiculo(placa, tipo, horas, esFrecuente))
                    contador++
                    println("Vehiculo registrado correctamente.")

                    if (contador == aforo) {
                        println("Aforo completo ($aforo/$aforo). No se pueden registrar mas vehiculos.")
                    }
                }
            }
            "2" -> {
                if (vehiculos.isEmpty()) {
                    println("No hay vehiculos registrados aun.")
                } else {
                    mostrarReporte(vehiculos)
                }
            }
            "3" -> {
                println("Cerrando sistema. Vehiculos registrados: $contador de $aforo")
                break
            }
            else -> println("Opcion invalida. Intente de nuevo.")
        }
    }
}