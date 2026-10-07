package com.duoc.cafeandino.model

data class UserPreferences(
    val customerName: String = "",
    val customerEmail: String = "",
    val defaultTipPercent: Int = 10,
    val darkMode: Boolean = false
)