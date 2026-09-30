// HomeScreen.kt
package com.duoc.cafeandino.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.duoc.cafeandino.R

// TopAppBar todavía es API experimental en Material 3: sin este @OptIn no compila
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    // Scaffold: estructura base de la pantalla (barra superior + contenido)
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Café Andino") })
        }
    ) { padding ->
        // Column: organiza los elementos de forma vertical
        Column(
            modifier = Modifier
                .padding(padding)      // evita que la barra superior tape el contenido
                .fillMaxSize()         // ocupa toda la pantalla
                .padding(16.dp),       // margen interno
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Image: el logo, desde res/drawable
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Café Andino",
                modifier = Modifier.size(80.dp)
            )

            Text(
                text = "Bienvenido a Café Andino",
                fontWeight = FontWeight.Bold
            )

            // Row: organiza elementos de forma horizontal
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Cappuccino - $2200", modifier = Modifier.weight(1f))
                Button(onClick = { /* acción a definir */ }) {
                    Text("Agregar")
                }
            }
        }
    }
}

// @Preview: permite ver la pantalla en el IDE sin correr el emulador
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}