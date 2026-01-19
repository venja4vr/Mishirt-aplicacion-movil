package com.example.mishirt_movil.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mishirt_movil.R

// Fuente Bebas Neue (res/font/bebas_nue_regular.ttf)
val BebasNeue = FontFamily(
    Font(R.font.bebas_nue_regular)
)

// Estilo solo para títulos de sección (Home)
val SectionTitleStyle = TextStyle(
    fontFamily = BebasNeue,
    fontWeight = FontWeight.Normal,
    fontSize = 28.sp,
    letterSpacing = 0.5.sp
)

// Fuente nueva para Detalle (res/font/zalando_sans_expanded_italic_variable.ttf)
val ZalandoSansExpanded = FontFamily(
    Font(
        resId = R.font.zalando_sans_expanded_italic_variable,
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    )
)

// Tipografías generales de la app (se dejan por defecto)
val Typography = Typography()
