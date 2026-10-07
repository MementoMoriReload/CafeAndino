package com.duoc.cafeandino.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.cafeandino.model.UserPreferences
import com.duoc.cafeandino.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserPreferencesRepository(application)

    // Lo guardado, convertido en un StateFlow que la pantalla puede observar
    val preferences: StateFlow<UserPreferences> = repository.userPreferences
        .stateIn(viewModelScope, SharingStarted.Eagerly, UserPreferences())

    // Guardar es una operación suspend: solo se puede llamar desde una corrutina
    fun onDarkModeChange(enabled: Boolean) {
        viewModelScope.launch { repository.setDarkMode(enabled) }
    }

    fun onDefaultTipChange(percent: Int) {
        viewModelScope.launch { repository.setDefaultTipPercent(percent) }
    }

    fun clearCustomerData() {
        viewModelScope.launch { repository.clearCustomerData() }
    }
}