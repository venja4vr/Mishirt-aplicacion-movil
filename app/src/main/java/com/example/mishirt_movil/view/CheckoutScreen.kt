package com.example.mishirt_movil.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mishirt_movil.model.CartItemUi
import com.example.mishirt_movil.model.CheckoutUiState
import com.example.mishirt_movil.model.Comuna
import com.example.mishirt_movil.model.DeliveryMethod
import com.example.mishirt_movil.ui.theme.MossGreen
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.saveable.rememberSaveable
import coil.compose.AsyncImage
import java.net.URLEncoder
import androidx.compose.runtime.*

@Composable
fun CheckoutScreen(
    items: List<CartItemUi>,
    subtotalCLP: Int,
    formatPrice: (Int) -> String,
    state: CheckoutUiState,
    onDeliveryMethodChange: (DeliveryMethod) -> Unit,
    onAddressChange: (String) -> Unit,
    onComunaSelected: (Comuna) -> Unit,
    onRetryComunas: () -> Unit,
    onConfirm: () -> Unit,
    onGoHome: () -> Unit
) {
    val context = LocalContext.current

    val subtotalText = formatPrice(subtotalCLP)
    val shippingText = formatPrice(state.shippingCLP)
    val totalText = formatPrice(subtotalCLP + state.shippingCLP)

    var pedidoConfirmado by rememberSaveable { mutableStateOf(false) }

    var qrSnapshot by rememberSaveable { mutableStateOf<String?>(null) }
    var snapshotSubtotal by rememberSaveable { mutableStateOf<String?>(null) }
    var snapshotShipping by rememberSaveable { mutableStateOf<String?>(null) }
    var snapshotTotal by rememberSaveable { mutableStateOf<String?>(null) }

    val snapshotItemsState = remember { mutableStateOf<List<CartItemUi>>(emptyList()) }

    val uiSubtotal = snapshotSubtotal ?: subtotalText
    val uiShipping = snapshotShipping ?: shippingText
    val uiTotal = snapshotTotal ?: totalText
    val uiItems = if (pedidoConfirmado) snapshotItemsState.value else items

    val itemsCountNow = (if (pedidoConfirmado) uiItems else items).sumOf { it.quantity }
    val qrDataFinal = qrSnapshot ?: "MISHirt pedido | items: $itemsCountNow | total: $uiTotal"
    val encodedData = URLEncoder.encode(qrDataFinal, "UTF-8")
    val qrUrl = "https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=$encodedData"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // checkout normal
        if (!pedidoConfirmado) {

            Text(
                text = "Resumen",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items, key = { "${it.productId}_${it.size}" }) { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "${item.title} x${item.quantity}")
                                Text(
                                    text = "Talla: ${item.size}",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(text = formatPrice(item.unitPriceCLP * item.quantity))
                        }
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            Text(
                text = "Entrega",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            DeliveryOptionRow(
                selected = state.deliveryMethod,
                onSelect = onDeliveryMethodChange
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (state.deliveryMethod == DeliveryMethod.DELIVERY) {
                OutlinedTextField(
                    value = state.address,
                    onValueChange = onAddressChange,
                    label = { Text("Dirección") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                ComunaDropdown(
                    comunas = state.comunas,
                    selected = state.selectedComuna,
                    isLoading = state.isLoadingComunas,
                    error = state.comunasError,
                    onSelect = onComunaSelected,
                    onRetry = onRetryComunas
                )

                if (!state.canConfirm) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Para delivery, complete dirección y seleccione una comuna.",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Subtotal")
                Text(subtotalText)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Envío")
                Text(shippingText)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total", style = MaterialTheme.typography.titleMedium)
                Text(totalText, style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    snapshotItemsState.value = items.toList()
                    snapshotSubtotal = subtotalText
                    snapshotShipping = shippingText
                    snapshotTotal = totalText

                    qrSnapshot =
                        "MISHirt pedido | items: ${items.sumOf { it.quantity }} | total: $totalText | " +
                                items.joinToString(separator = " ; ") {
                                    "${it.title}(${it.size})x${it.quantity}"
                                }

                    onConfirm()
                    pedidoConfirmado = true

                    Toast.makeText(context, "Pedido confirmado", Toast.LENGTH_SHORT).show()
                },
                enabled = items.isNotEmpty() && state.canConfirm,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MossGreen,
                    contentColor = Color.White
                )
            ) {
                Text("Confirmar pedido")
            }
        }


        //Pedido confirmado (QR + items)

        if (pedidoConfirmado) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "QR del pedido",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            AsyncImage(
                model = qrUrl,
                contentDescription = "qr del pedido",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(220.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar items confirmados
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Resumen", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    uiItems.forEach { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "${item.title} x${item.quantity}")
                                Text(
                                    text = "Talla: ${item.size}",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(text = formatPrice(item.unitPriceCLP * item.quantity))
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    Divider(modifier = Modifier.padding(vertical = 10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total", style = MaterialTheme.typography.titleMedium)
                        Text(uiTotal, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onGoHome() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MossGreen,
                    contentColor = Color.White
                )
            ) {
                Text("Volver al inicio")
            }
        }
    }
}
@Composable
private fun DeliveryOptionRow(
    selected: DeliveryMethod,
    onSelect: (DeliveryMethod) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DeliveryOption(
            title = "Retiro",
            selected = selected == DeliveryMethod.PICKUP,
            onClick = { onSelect(DeliveryMethod.PICKUP) }
        )
        DeliveryOption(
            title = "Delivery",
            selected = selected == DeliveryMethod.DELIVERY,
            onClick = { onSelect(DeliveryMethod.DELIVERY) }
        )
    }
}

@Composable
private fun DeliveryOption(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(text = title)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ComunaDropdown(
    comunas: List<Comuna>,
    selected: Comuna?,
    isLoading: Boolean,
    error: String?,
    onSelect: (Comuna) -> Unit,
    onRetry: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    if (error != null) {
        Text(
            text = "No se pudieron cargar comunas: $error",
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(onClick = onRetry) { Text("Reintentar") }
        Spacer(modifier = Modifier.height(10.dp))
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            if (!isLoading && comunas.isNotEmpty()) expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = selected?.nombre ?: "",
            onValueChange = {},
            readOnly = true,
            enabled = !isLoading && comunas.isNotEmpty(),
            label = { Text(if (isLoading) "Cargando comunas..." else "Comuna") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            comunas.forEach { comuna ->
                DropdownMenuItem(
                    text = { Text(comuna.nombre) },
                    onClick = {
                        onSelect(comuna)
                        expanded = false
                    }
                )
            }
        }
    }
}



