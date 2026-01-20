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
import com.example.mishirt_movil.ui.theme.ZalandoSansExpanded

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: ProductUi,
    onAddToCart: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedSize by rememberSaveable { mutableStateOf<String?>(null) }

    val titleStyle = MaterialTheme.typography.titleLarge.copy(fontFamily = ZalandoSansExpanded)
    val priceStyle = MaterialTheme.typography.titleMedium.copy(fontFamily = ZalandoSansExpanded)
    val sectionTitleStyle = MaterialTheme.typography.titleMedium.copy(fontFamily = ZalandoSansExpanded)
    val bodyStyle = MaterialTheme.typography.bodyMedium.copy(fontFamily = ZalandoSansExpanded)

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
            Text(text = product.title, style = titleStyle)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.price,
                style = priceStyle,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Descripción (título + texto debajo)
        if (product.description.isNotBlank()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "Descripción", style = sectionTitleStyle)
                    Text(text = product.description, style = bodyStyle)
                }
            }
        }

        // Material (título + texto debajo)
        if (product.material.isNotBlank()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "Material", style = sectionTitleStyle)
                    Text(text = product.material, style = bodyStyle)
                }
            }
        }


        if (product.continent.isNotBlank()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "Continente", style = sectionTitleStyle)
                    Text(text = product.continent, style = bodyStyle)
                }
            }
        }


        item {
            Text(text = "Tallas disponibles", style = sectionTitleStyle)
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(product.sizes) { size ->
                    val selected = selectedSize == size
                    FilterChip(
                        selected = selected,
                        onClick = { selectedSize = size },
                        label = { Text(size, style = bodyStyle) }
                    )
                }
            }
        }

        item {
            Button(
                onClick = {
                    if (selectedSize == null) {
                        Toast.makeText(context, "Selecciona una talla", Toast.LENGTH_SHORT).show()
                    } else {
                        onAddToCart(selectedSize!!)
                        Toast.makeText(context, "Agregado al carrito", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MossGreen,
                    contentColor = Color.White
                )
            ) {
                Text("agregar al carrito", style = bodyStyle.copy(color = Color.White))
            }
        }
    }
}
