package com.example.mishirt_movil.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import com.example.mishirt_movil.ui.theme.MossGreen
import androidx.compose.foundation.layout.statusBarsPadding



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String = "MISHirt",
    onTitleClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = {
            // title actua como boton para ir al home
            Text(
                text = title,
                color = Color.White,
                modifier = Modifier.clickable { onTitleClick() }
            )

        },
        actions = {
            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "perfil",
                    tint = Color.White
                )
            }
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "ajustes",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MossGreen
        )
    )
}
