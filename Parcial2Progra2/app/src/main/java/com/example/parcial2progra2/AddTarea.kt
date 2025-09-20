package com.example.parcial2progra2
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AddTareaScrn(navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()

    var nombre by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var responsable by remember { mutableStateOf("") }

    var mensaje by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Crear Nueva Tarea", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Tarea") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha: dd/mm/yyyy") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = responsable,
            onValueChange = { responsable = it },
            label = { Text("Nombre del responsable") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val error = validarTarea(nombre, fecha, responsable)
                if (error != null) {
                    mensaje = "$error"
                } else {
                    val nuevaTarea = Tarea(nombre, fecha, responsable)
                    db.collection("tareas")
                        .add(nuevaTarea)
                        .addOnSuccessListener {
                            mensaje = "Tarea guardada"
                            navController.popBackStack()
                        }
                        .addOnFailureListener { e ->
                            mensaje = " Error al guardar: ${e.message}"
                        }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        {
            Text("Guardar")
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (mensaje.isNotEmpty()) {
            Text(mensaje, color = MaterialTheme.colorScheme.primary)
        }
    }
}

