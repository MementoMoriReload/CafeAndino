// Routes.kt
package com.duoc.cafeandino.ui.navigation

object Routes {
    const val HOME = "home"
    const val PRODUCT_DETAIL = "productDetail/{productId}"
    const val CART = "cart"
    const val CHECKOUT = "checkout"
    const val SETTINGS = "settings"
    const val CONFIRMATION = "confirmation/{customerName}"

    fun productDetail(productId: Int) = "productDetail/$productId"
    fun confirmation(customerName: String) = "confirmation/$customerName"
}