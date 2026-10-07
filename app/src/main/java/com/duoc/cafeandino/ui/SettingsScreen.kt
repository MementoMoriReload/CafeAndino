package com.duoc.cafeandino.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.duoc.cafeandino.model.UserPreferences
import com.duoc.cafeandino.viewmodel.SettingsViewModel
import kotlin.math.roundToInt

// Esta función conoce al ViewModel
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    onBack: () -> Unit
) {
    val preferences by settingsViewModel.preferences.collectAsState()

    SettingsScreenContent(
        preferences = preferences,
        onDarkModeChange = { enabled -> settingsViewModel.onDarkModeChange(enabled) },
        onDefaultTipChange = { percent -> settingsViewModel.onDefaultTipChange(percent) },
        onClearCustomerData = { settingsViewModel.clearCustomerData() },
        onBack = onBack
    )
}

// Esta función NO conoce al ViewModel: solo dibuja lo que recibe
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    preferences: UserPreferences,
    onDarkModeChange: (Boolean) -> Unit,
    onDefaultTipChange: (Int) -> Unit,
    onClearCustomerData: () -> Unit,
    onBack: () -> Unit
) {
    var showClearDialog by rememberSaveable { mutableStateOf(false) }

    // Valor del slider mientras el dedo lo arrastra: se guarda recién al soltarlo
    var sliderValue by remember(preferences.defaultTipPercent) {
        mutableFloatStateOf(preferences.defaultTipPercent.toFloat())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajustes") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Volver") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ---------- APARIENCIA ----------
            Text(text = "Apariencia", fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Modo oscuro", modifier = Modifier.weight(1f))
                Switch(
                    checked = preferences.darkMode,
                    onCheckedChange = onDarkModeChange
                )
            }

            HorizontalDivider()

            // ---------- PROPINA POR DEFECTO ----------
            Text(
                text = "Propina por defecto: ${sliderValue.roundToInt()}%",
                fontWeight = FontWeight.Bold
            )
            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                onValueChangeFinished = { onDefaultTipChange(sliderValue.roundToInt()) },
                valueRange = 0f..20f,
                steps = 4
            )
            Text(
                text = "Es la propina con la que parte el formulario de pedido.",
                style = MaterialTheme.typography.bodySmall
            )

            HorizontalDivider()

            // ---------- DATOS GUARDADOS ----------
            Text(text = "Datos guardados del cliente", fontWeight = FontWeight.Bold)
            if (preferences.customerName.isBlank() && preferences.customerEmail.isBlank()) {
                Text(text = "No hay datos guardados")
            } else {
                Text(text = "Nombre: ${preferences.customerName}")
                Text(text = "Correo: ${preferences.customerEmail}")
            }
            OutlinedButton(
                onClick = { showClearDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Borrar datos guardados")
            }
        }
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("Borrar datos guardados") },
            text = { Text("Se olvidarán el nombre y el correo del cliente. ¿Continuar?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClearCustomerData()
                        showClearDialog = false
                    }
                ) {
                    Text("Borrar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreenContent(
        preferences = UserPreferences(
            customerName = "Camila Rojas",
            customerEmail = "camila@correo.cl",
            defaultTipPercent = 15,
            darkMode = false
        ),
        onDarkModeChange = {},
        onDefaultTipChange = {},
        onClearCustomerData = {},
        onBack = {}
    )
}