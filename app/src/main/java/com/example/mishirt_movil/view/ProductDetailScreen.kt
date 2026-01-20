package com.example.mishirt_movil.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mishirt_movil.model.ProductUi
import com.example.mishirt_movil.ui.theme.ZalandoSansExpanded

@Composable
fun ProductDetailScreen(
    product: ProductUi,
    modifier: Modifier = Modifier
) {
    val scroll = rememberScrollState()
    val selectedSize = remember { mutableStateOf(product.sizes.firstOrNull()) }

    Column(
        modifier = modifier
            .verticalScroll(scroll)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(id = product.imageRes),
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        )

        Text(
            text = product.title,
            style = MaterialTheme.typography.headlineSmall.copy(fontFamily = ZalandoSansExpanded)
        )

        Text(
            text = product.price,
            style = MaterialTheme.typography.titleLarge.copy(fontFamily = ZalandoSansExpanded),
            color = MaterialTheme.colorScheme.primary
        )

        if (product.description.isNotBlank()) {
            Text(
                text = "Descripción",
                style = MaterialTheme.typography.titleMedium.copy(fontFamily = ZalandoSansExpanded)
            )
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium.copy(fontFamily = ZalandoSansExpanded)
            )
        }

        if (product.material.isNotBlank()) {
            Text(
                text = "Material",
                style = MaterialTheme.typography.titleMedium.copy(fontFamily = ZalandoSansExpanded)
            )
            Text(
                text = product.material,
                style = MaterialTheme.typography.bodyMedium.copy(fontFamily = ZalandoSansExpanded)
            )
        }

        if (product.continent.isNotBlank()) {
            Text(
                text = "Continente",
                style = MaterialTheme.typography.titleMedium.copy(fontFamily = ZalandoSansExpanded)
            )
            Text(
                text = product.continent,
                style = MaterialTheme.typography.bodyMedium.copy(fontFamily = ZalandoSansExpanded)
            )
        }

        if (product.sizes.isNotEmpty()) {
            Text(
                text = "Tallas disponibles",
                style = MaterialTheme.typography.titleMedium.copy(fontFamily = ZalandoSansExpanded)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                product.sizes.forEach { size ->
                    val isSelected = selectedSize.value == size

                    OutlinedButton(
                        onClick = { selectedSize.value = size }
                    ) {
                        Text(
                            text = if (isSelected) "✓ $size" else size,
                            style = MaterialTheme.typography.bodyMedium.copy(fontFamily = ZalandoSansExpanded)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}
