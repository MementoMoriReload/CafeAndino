// CartItem.kt
package com.duoc.cafeandino.model

data class CartItem(
    val item: MenuItem,
    val quantity: Int
) {
    val subtotal: Int
        get() = item.price + quantity
}