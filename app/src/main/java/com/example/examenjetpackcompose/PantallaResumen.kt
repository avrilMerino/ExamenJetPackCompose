package com.example.examenjetpackcompose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaResumen(
    pedido: List<ItemPedido>,
    tamanoPizza: String,
    onVolver: () -> Unit
) {

    // =============================
    // VARIABLES DERIVADAS
    // =============================

    // Subtotal = suma de precio * cantidad SIN IVA
    val subtotal by remember(pedido) {
        derivedStateOf {
            pedido.sumOf { it.producto.precio * it.cantidad }
        }
    }

    // Coste segun el tamaño de la pizza
    val tamano by remember(tamanoPizza) {
        derivedStateOf {
            when {
                tamanoPizza == "Pequeña" -> 5.00
                tamanoPizza == "Mediana" -> 10.00
                tamanoPizza == "Grande" -> 15.00
                else -> 0.0 //pues si no ha seleccionado tamaño es que no ha seleccionado pizza asi que 0
            }
        }
    }

    // Total final CON SU IVA
    val totalConIva by remember(subtotal, tamano) {
        derivedStateOf {
            (subtotal + tamano) + ((subtotal + tamano) * 0.21)
        }
    }

    // =======================================================================================
    // UI la interfaz grafica de este proyecto es correcta y fiel a lo que se pide
    // =======================================================================================

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resumen del pedido") }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // -----------------------------
            // LISTA DE PRODUCTOS
            // -----------------------------
            Text("Detalles del pedido", fontWeight = FontWeight.Bold)

            if (pedido.isEmpty()) {
                Text("No hay productos en el pedido")
            } else {
                pedido.forEach { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${item.producto.nombre} x${item.cantidad}")
                        Text(String.format("%.2f€", item.producto.precio * item.cantidad))
                    }
                }
            }

            Divider()

            // -----------------------------
            // DESGLOSE
            // -----------------------------
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Subtotal")
                Text(String.format("%.2f€", subtotal))
            }


            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("TAMAÑO  ($tamanoPizza)")
                Text(String.format("%.2f€", tamano))
            }

            Divider(thickness = 2.dp)

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("TOTAL A PAGAR", fontWeight = FontWeight.Bold)
                Text(String.format("%.2f€", totalConIva), fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(16.dp))


            // -----------------------------
            // BOTÓN de confirmar el pedido
            // -----------------------------
            OutlinedButton(
                onClick = onVolver, //como el boton confirmar pedido no hace nada yo voy a hacer que regrese a la pantalla de incio
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar Pedido")
            }

            // -----------------------------
            // BOTÓN MODIFICAR
            // -----------------------------
            OutlinedButton(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Modificar Pedido")
            }
        }
    }
}