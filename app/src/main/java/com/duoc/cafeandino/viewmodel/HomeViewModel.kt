// HomeViewModel.kt
package com.duoc.cafeandino.viewmodel

import androidx.lifecycle.ViewModel
import com.duoc.cafeandino.model.MenuItem
import com.duoc.cafeandino.repository.MenuRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val repository: MenuRepository = MenuRepository()
) : ViewModel() {

    // _menuItems es la versión editable; menuItems es la de solo lectura que ve la pantalla
    private val _menuItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val menuItems: StateFlow<List<MenuItem>> = _menuItems.asStateFlow()

    // init se ejecuta apenas se crea el ViewModel: carga el menú una sola vez
    init {
        _menuItems.value = repository.getMenu()
    }
}