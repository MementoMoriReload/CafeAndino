// PaymentMethod.kt
package com.duoc.cafeandino.model

enum class PaymentMethod(val label: String) {
    CASH("Efectivo"),
    DEBIT("Tarjeta de débito"),
    CREDIT("Tarjeta de crédito")
}