package com.example.myfinance.model

// Modelo de dados das movimentações financeiras
data class Movimentacao(
    val id: Int,
    val descricao: String,
    val categoria: String,
    val valor: Double,
    val tipo: String,
    val data: String
)
