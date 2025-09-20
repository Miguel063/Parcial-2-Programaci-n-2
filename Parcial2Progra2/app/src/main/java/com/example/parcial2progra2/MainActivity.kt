package com.example.parcial2progra2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parcial2progra2.ui.theme.Parcial2Progra2Theme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Parcial2Progra2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MainScreens()
                    }
                }
            }
        }
    }
}
fun validarTarea(nombre: String, fecha: String, responsable: String): String? {
    if (nombre.isBlank() || nombre.length < 3) return "El nombre no es valido"
    if (!fecha.matches(Regex("\\d{2}/\\d{2}/\\d{4}")))  return "La fecha debe tener el formato DD-MM-YYYY"
    if (responsable.isBlank()) return "El responsable no puede estar vacío"
    return null
}
data class Tarea(
    val nombre: String = "",
    val fecha: String = "",
    val responsable: String = ""
)

@Composable
fun MainScreens() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "listaTareas") {
        composable("listaTareas") { TareaListaScrn(navController) }

        composable("nuevaTarea") { AddTareaScrn(navController) }
    }
}
