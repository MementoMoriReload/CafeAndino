package com.duoc.cafeandino.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.duoc.cafeandino.model.UserPreferences
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(private val context: Context) {

    // El acceso al archivo "user_preferences" donde Android guarda los datos
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    // Cada dato se guarda bajo su propia clave: un nombre de texto que identifica el dato
    private object Keys {
        val CUSTOMER_NAME = stringPreferencesKey("customer_name")
        val CUSTOMER_EMAIL = stringPreferencesKey("customer_name")
        val DEFAULT_TIP_PERCENT = intPreferencesKey("default_tip_percent")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
    }

    // Un Flow: entrega lo guardado ahora y vuelve a entregar cada vez que algo cambia
    val userPreferences: Flow<UserPreferences> = context.dataStore.data
        .catch { error ->
            // Si el archivo no se pudo leer, partimos como si estuviera vacío
            if (error is IOException) emit(emptyPreferences()) else throw error
        }
        .map { preferences ->
            UserPreferences(
                customerName = preferences[Keys.CUSTOMER_NAME].toString(),
                customerEmail = preferences[Keys.CUSTOMER_EMAIL].toString(),
                defaultTipPercent = preferences[Keys.DEFAULT_TIP_PERCENT] ?: 10,
                darkMode = preferences[Keys.DARK_MODE] ?: false
            )
        }

    suspend fun saveCustomerData(name: String, email: String) {
        context.dataStore.edit { preferences ->
            preferences[Keys.CUSTOMER_NAME] = name
            preferences[Keys.CUSTOMER_EMAIL] = email
        }
    }

    suspend fun setDefaultTipPercent(percent: Int) {
        context.dataStore.edit { preferences ->
            preferences[Keys.DEFAULT_TIP_PERCENT] = percent
        }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[Keys.DARK_MODE] = enabled
        }
    }

    // Olvida los datos guardados del cliente
    suspend fun clearCustomerData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}