// MenuRepository.kt
package com.duoc.cafeandino.repository

import com.duoc.cafeandino.R
import com.duoc.cafeandino.model.MenuItem

class MenuRepository {
    fun getMenu(): List<MenuItem> = listOf(
        MenuItem(1, "Café Americano", "Café negro suave, 250ml", 1800, R.drawable.logo),
        MenuItem(2, "Cappuccino", "Espresso con leche vaporizada", 2200, R.drawable.logo),
        MenuItem(3, "Croissant", "Croissant de mantequilla artesanal", 1500, R.drawable.logo)
    )
}