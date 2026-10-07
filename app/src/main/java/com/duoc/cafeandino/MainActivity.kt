// MainActivity.kt
package com.duoc.cafeandino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.duoc.cafeandino.ui.navigation.AppNavHost
import com.duoc.cafeandino.ui.theme.CafeAndinoTheme
import com.duoc.cafeandino.viewmodel.CartViewModel
import com.duoc.cafeandino.viewmodel.CheckoutViewModel
import com.duoc.cafeandino.viewmodel.HomeViewModel
import com.duoc.cafeandino.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {

    // Cada ViewModel se crea una sola vez y sobrevive al giro de pantalla
    private val homeViewModel: HomeViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val checkoutViewModel: CheckoutViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // El tema depende de lo guardado: si cambia el ajuste, cambia toda la app
            val preferences by settingsViewModel.preferences.collectAsState()

            CafeAndinoTheme(darkTheme = preferences.darkMode) {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    homeViewModel = homeViewModel,
                    cartViewModel = cartViewModel,
                    checkoutViewModel = checkoutViewModel,
                    settingsViewModel = settingsViewModel
                )
            }
        }
    }
}