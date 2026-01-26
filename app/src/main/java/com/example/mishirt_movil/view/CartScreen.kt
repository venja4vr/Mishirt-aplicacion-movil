package com.example.mishirt_movil.view

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mishirt_movil.model.CartItemUi
import com.example.mishirt_movil.ui.theme.MossGreen

@Composable
fun CartScreen(
    items: List<CartItemUi>,
    totalText: String,
    formatPrice: (Int) -> String,
    onIncrease: (String, String) -> Unit,
    onDecrease: (String, String) -> Unit,
    onRemove: (String, String) -> Unit,
    onClear: () -> Unit,
    onCheckout: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        if (items.isEmpty()) {
            Text(
                text = "carrito vacío",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "agrega productos desde el detalle.")
            return
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = items,
                key = { "${it.productId}_${it.size}" }
            ) { item ->
                CartItemCard(
                    item = item,
                    lineTotalText = formatPrice(item.unitPriceCLP * item.quantity),
                    onIncrease = { onIncrease(item.productId, item.size) },
                    onDecrease = { onDecrease(item.productId, item.size) },
                    onRemove = { onRemove(item.productId, item.size) }
                )
            }
        }

        Divider(modifier = Modifier.padding(vertical = 12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "total")
            Text(
                text = totalText,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onClear,
                modifier = Modifier.weight(1f)
            ) {
                Text("vaciar carrito")
            }

            Button(
                onClick = {
                    Toast.makeText(context, "continuar a checkout", Toast.LENGTH_SHORT).show()
                    onCheckout()
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MossGreen,
                    contentColor = Color.White
                )
            ) {
                Text("continuar")
            }
        }
    }
}

@Composable
private fun CartItemCard(
    item: CartItemUi,
    lineTotalText: String,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(82.dp)
                    .padding(end = 12.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "talla: ${item.size}")
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "precio: ${item.unitPriceText}")
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "subtotal: $lineTotalText")
            }

            Column(horizontalAlignment = Alignment.End) {

                IconButton(onClick = onRemove) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "eliminar"
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onDecrease) {
                        Icon(
                            imageVector = Icons.Filled.Remove,
                            contentDescription = "restar"
                        )
                    }
                    Text(text = item.quantity.toString())
                    IconButton(onClick = onIncrease) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "sumar"
                        )
                    }
                }
            }
        }
    }
}
