package com.example.util

import kotlin.math.round
import kotlin.math.pow

fun Double.format(decimals: Int = 2): String {
    val factor = 10.0.pow(decimals)
    val rounded = round(this * factor) / factor
    
    val parts = rounded.toString().split(".")
    val intPart = parts[0]
    val decPart = if (parts.size > 1) parts[1] else ""
    
    return if (decimals > 0) {
        val paddedDec = decPart.padEnd(decimals, '0').take(decimals)
        "$intPart.$paddedDec"
    } else {
        intPart
    }
}
