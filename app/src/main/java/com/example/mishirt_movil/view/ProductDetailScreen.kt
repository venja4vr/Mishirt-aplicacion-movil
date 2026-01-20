package com.example.mishirt_movil.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mishirt_movil.model.ProductUi
import com.example.mishirt_movil.ui.theme.MossGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: ProductUi,
    onAddToCart: (String) -> Unit,
    onGoToCart: () -> Unit
) {
    val context = LocalContext.current
    var selectedSize by rememberSaveable { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Card(shape = RoundedCornerShape(16.dp)) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                )
            }
        }

        item {
            Text(text = product.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.price,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (product.description.isNotBlank()) {
            item { Text(text = product.description) }
        }

        if (product.material.isNotBlank()) {
            item { Text(text = "material: ${product.material}") }
        }

        item {
            Text(text = "talla", style = MaterialTheme.typography.titleMedium)
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(product.sizes) { size ->
                    val selected = selectedSize == size
                    FilterChip(
                        selected = selected,
                        onClick = { selectedSize = size },
                        label = { Text(size) }
                    )
                }
            }
        }

        item {
            Button(
                onClick = {
                    if (selectedSize == null) {
                        Toast.makeText(context, "selecciona una talla", Toast.LENGTH_SHORT).show()
                    } else {
                        onAddToCart(selectedSize!!)
                        Toast.makeText(context, "agregado al carrito", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MossGreen,
                    contentColor = Color.White
                )
            ) {
                Text("agregar al carrito")
            }
        }

        item {
            OutlinedButton(
                onClick = onGoToCart,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("ver carrito")
            }
        }
    }
}
