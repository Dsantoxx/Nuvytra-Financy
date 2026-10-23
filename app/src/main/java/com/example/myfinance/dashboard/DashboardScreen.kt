package com.example.myfinance.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfinance.components.CardMovimentacao
import com.example.myfinance.model.Movimentacao
import com.example.myfinance.utils.*

@Composable
fun DashboardScreen(movimentacoes: List<Movimentacao>) {

    val receitas = movimentacoes
        .filter { it.tipo == "Receita" }
        .sumOf { it.valor }

    val despesas = movimentacoes
        .filter { it.tipo == "Despesa" }
        .sumOf { it.valor }

    val saldo = receitas - despesas
    val total = receitas + despesas

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Fundo)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        item {
            Text(
                "Visão geral",
                fontSize = 25.sp,
                color = TextoPrincipal,
                fontWeight = FontWeight.Bold
            )

            Text(
                "Acompanhe suas finanças em um só lugar.",
                color = TextoSecundario,
                fontSize = 14.sp
            )
        }

        // Cartão principal
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Verde
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        "SALDO DISPONÍVEL",
                        color = VerdeClaro,
                        fontSize = 13.sp
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        formatarDinheiro(saldo),
                        color = Branco,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        "Seu resumo financeiro",
                        color = VerdeClaro,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Receitas e despesas
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(Modifier.weight(1f)) {
                    CardFinanceiro(
                        "Receitas",
                        receitas,
                        VerdeDestaque
                    )
                }

                Box(Modifier.weight(1f)) {
                    CardFinanceiro(
                        "Despesas",
                        despesas,
                        Vermelho
                    )
                }
            }
        }

        item {
            Text(
                "Resumo financeiro",
                fontSize = 20.sp,
                color = TextoPrincipal,
                fontWeight = FontWeight.Bold
            )
        }

        // Indicadores
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Branco
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {

                    Text(
                        "Distribuição das movimentações",
                        fontWeight = FontWeight.SemiBold,
                        color = TextoPrincipal
                    )

                    Spacer(Modifier.height(20.dp))

                    Text(
                        "Receitas",
                        color = TextoSecundario
                    )

                    Spacer(Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = {
                            if (total > 0)
                                (receitas / total).toFloat()
                            else 0f
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp),
                        color = VerdeDestaque,
                        trackColor = VerdeClaro
                    )

                    Spacer(Modifier.height(18.dp))

                    Text(
                        "Despesas",
                        color = TextoSecundario
                    )

                    Spacer(Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = {
                            if (total > 0)
                                (despesas / total).toFloat()
                            else 0f
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp),
                        color = Vermelho,
                        trackColor = VerdeClaro
                    )
                }
            }
        }

        item {
            Text(
                "Últimas movimentações",
                fontSize = 20.sp,
                color = TextoPrincipal,
                fontWeight = FontWeight.Bold
            )
        }

        items(movimentacoes.takeLast(3).reversed()) { item ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Branco
                )
            ) {
                Box(
                    modifier = Modifier.padding(14.dp)
                ) {
                    CardMovimentacao(item)
                }
            }
        }
    }
}

@Composable
fun CardFinanceiro(
    titulo: String,
    valor: Double,
    cor: Color
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Branco
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                titulo,
                color = TextoSecundario,
                fontSize = 13.sp
            )

            Spacer(Modifier.height(10.dp))

            Text(
                formatarDinheiro(valor),
                color = cor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
