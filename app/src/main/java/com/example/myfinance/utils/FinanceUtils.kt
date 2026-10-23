package com.example.myfinance.utils

import androidx.compose.ui.graphics.Color
import java.text.NumberFormat
import java.util.Locale

// Identidade visual Nuvytra Financy
val Verde = Color(0xFF104A32)
val VerdeDestaque = Color(0xFF22A65A)
val VerdeClaro = Color(0xFFE8F7ED)
val Branco = Color(0xFFFFFFFF)
val Fundo = Color(0xFFF7FAF8)
val TextoPrincipal = Color(0xFF18352A)
val TextoSecundario = Color(0xFF718078)
val Vermelho = Color(0xFFDC3545)

fun formatarDinheiro(valor: Double): String {
    return NumberFormat.getCurrencyInstance(
        Locale.forLanguageTag("pt-BR")
    ).format(valor)
}
