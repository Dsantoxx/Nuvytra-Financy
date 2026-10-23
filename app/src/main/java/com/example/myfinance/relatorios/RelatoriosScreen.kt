
package com.example.myfinance.relatorios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfinance.model.Movimentacao
import com.example.myfinance.utils.*
import java.text.NumberFormat
import java.util.Locale

@Composable
fun RelatoriosScreen(
    movimentacoes: List<Movimentacao>
) {
    val receitas = movimentacoes
        .filter { it.tipo.equals("Receita", ignoreCase = true) }
        .sumOf { it.valor }

    val despesas = movimentacoes
        .filter { it.tipo.equals("Despesa", ignoreCase = true) }
        .sumOf { it.valor }

    val saldo = receitas - despesas

    val moeda = NumberFormat.getCurrencyInstance(
        Locale("pt", "BR")
    )

    val gastosPorCategoria = movimentacoes
        .filter { it.tipo.equals("Despesa", ignoreCase = true) }
        .groupBy { it.categoria }
        .mapValues { (_, itens) -> itens.sumOf { it.valor } }
        .toList()
        .sortedByDescending { it.second }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fundo)
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Relatórios financeiros",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Verde
        )

        Text(
            text = "Acompanhe o desempenho das suas finanças.",
            color = TextoSecundario
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Verde
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "Saldo disponível",
                    color = Color.White.copy(alpha = 0.85f)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    moeda.format(saldo),
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ResumoCard(
                titulo = "Receitas",
                valor = moeda.format(receitas),
                cor = Verde,
                modifier = Modifier.weight(1f)
            )
            ResumoCard(
                titulo = "Despesas",
                valor = moeda.format(despesas),
                cor = Color(0xFFDC3545),
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            text = "Gastos por categoria",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (gastosPorCategoria.isEmpty()) {
                    Text("Nenhuma despesa cadastrada.")
                } else {
                    gastosPorCategoria.forEach { (categoria, total) ->
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.SpaceBetween
                            ) {
                                Text(
                                    categoria,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    moeda.format(total),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Spacer(Modifier.height(7.dp))

                            LinearProgressIndicator(
                                progress = {
                                    if (despesas > 0)
                                        (total / despesas)
                                            .toFloat()
                                            .coerceIn(0f, 1f)
                                    else 0f
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp),
                                color = Verde,
                                trackColor = Color(0xFFE8F3EC)
                            )
                        }
                    }
                }
            }
        }

        Text(
            text = "Resumo geral",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text("Movimentações: ${movimentacoes.size}")
                Spacer(Modifier.height(8.dp))
                Text("Categorias com despesas: ${gastosPorCategoria.size}")
                Spacer(Modifier.height(8.dp))
                Text(
                    if (saldo >= 0)
                        "Seu saldo está positivo."
                    else
                        "Atenção: suas despesas superam suas receitas.",
                    color = if (saldo >= 0)
                        Verde
                    else
                        Color(0xFFDC3545),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun ResumoCard(
    titulo: String,
    valor: String,
    cor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                titulo,
                color = TextoSecundario,
                fontSize = 13.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                valor,
                color = cor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
