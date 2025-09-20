package com.example.parcial2progra2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun TareaListaScrn(navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()
    var tareas by remember { mutableStateOf(listOf<Tarea>()) }

    LaunchedEffect(Unit) {
        db.collection("tareas").addSnapshotListener { value, _ ->
            if (value != null) {
                tareas = value.documents.mapNotNull { it.toObject(Tarea::class.java) }
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Lista de Tareas", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(tareas) { tarea ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Nombre: ${tarea.nombre}")
                        Text("Fecha: ${tarea.fecha}")
                        Text("Responsable: ${tarea.responsable}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { navController.navigate("nuevaTarea") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nueva Tarea")
        }
    }
}

