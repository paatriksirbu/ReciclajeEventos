package com.example.reciclajeeventos

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.example.reciclajeeventos.ui.theme.ReciclajeEventosTheme
import java.security.AccessController

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val composeView = findViewById<ComposeView>(R.id.compose_view)


        composeView.setContent {
            ReciclajeEventosTheme {
                val navController = rememberNavController()
                PantallaPrincipal(navController)
            }
        }

    }

    @Composable
    fun PantallaPrincipal(navController: NavController) {

        ReciclajeEventosTheme {
            var showButtons by remember { mutableStateOf(false) }
            var greetingText by remember { mutableStateOf("Bienvenido, EcoAmigo!") }
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                //De esta manera solo mostramos el texto de saludo cuando no se han mostrado las opciones.
                if (!showButtons) {
                    Text(text = greetingText)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                //Creamos un boton principal para mostrar las opciones

                if (!showButtons && greetingText.equals("Bienvenido, EcoAmigo!")) {
                    Button(
                        onClick = { showButtons = true },
                        modifier = Modifier.padding(vertical = 8.dp).height(50.dp)
                    ) {
                        Text(text = "Mostrar opciones")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                if (showButtons) {
                    Button(onClick = { navController.navigate("calendar") }) {
                        Text(text = "Calendario")
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = { navController.navigate("mapa") }) {
                        Text(text = "Mapa")
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = { navController.navigate("estadisticas") }) {
                        Text(text = "Estadisticas")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        }

    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ReciclajeEventosTheme {
            PantallaPrincipal(rememberNavController())
        }
    }
}