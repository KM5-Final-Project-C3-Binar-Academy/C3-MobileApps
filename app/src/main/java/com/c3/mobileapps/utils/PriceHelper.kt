package com.c3.mobileapps.utils

fun Int.formatAsPrice(): String {
    val priceValue = this.toLong() ?: 0
    return String.format("Rp. %,d", priceValue)
}
fun Double.formatAsPrice(): String {
    val priceValue = this.toLong() ?: 0
    return String.format("Rp. %,d", priceValue)
}