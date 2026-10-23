
package com.example.myfinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.myfinance.dashboard.DashboardScreen
import com.example.myfinance.historico.HistoricoScreen
import com.example.myfinance.login.LoginScreen
import com.example.myfinance.model.Movimentacao
import com.example.myfinance.movimentacoes.NovaMovimentacaoScreen
import com.example.myfinance.relatorios.RelatoriosScreen
import com.example.myfinance.utils.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Verde,
                    secondary = VerdeDestaque,
                    background = Fundo,
                    surface = Branco,
                    onPrimary = Branco,
                    onBackground = TextoPrincipal
                )
            ) {
                var logado by remember {
                    mutableStateOf(false)
                }

                if (logado) {
                    NuvytraApp(
                        onLogout = {
                            logado = false
                        }
                    )
                } else {
                    LoginScreen(
                        onLoginSuccess = {
                            logado = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NuvytraApp(onLogout: () -> Unit) {

    var telaAtual by remember {
        mutableStateOf("dashboard")
    }

    val movimentacoes = remember {
        mutableStateListOf(
            Movimentacao(
                1, "Salário", "Trabalho",
                2500.0, "Receita", "20/10/2026"
            ),
            Movimentacao(
                2, "Supermercado", "Alimentação",
                350.0, "Despesa", "20/10/2026"
            ),
            Movimentacao(
                3, "Internet", "Contas",
                100.0, "Despesa", "19/10/2026"
            ),
            Movimentacao(
                4, "Freelance", "Trabalho",
                500.0, "Receita", "18/10/2026"
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fundo)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Verde)
                .padding(
                    horizontal = 20.dp,
                    vertical = 18.dp
                )
        ) {
            Text(
                text = "Nuvytra Financy",
                color = Branco,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "Seu dinheiro, seu controle.",
                color = VerdeClaro,
                fontSize = 13.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Branco)
                .horizontalScroll(rememberScrollState())
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val telas = listOf(
                "dashboard" to "Início",
                "historico" to "Histórico",
                "nova" to "Nova movimentação",
                "relatorios" to "Relatórios"
            )

            telas.forEach { (rota, titulo) ->
                Button(
                    onClick = { telaAtual = rota },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if (telaAtual == rota) Verde
                            else VerdeClaro,
                        contentColor =
                            if (telaAtual == rota) Branco
                            else Verde
                    )
                ) {
                    Text(titulo)
                }
            }
        }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            when (telaAtual) {

                "dashboard" -> {
                    DashboardScreen(movimentacoes)
                }

                "historico" -> {
                    HistoricoScreen(movimentacoes)
                }

                "nova" -> {
                    NovaMovimentacaoScreen(
                        onSalvar = { novaMovimentacao ->
                            val novoId =
                                (movimentacoes.maxOfOrNull {
                                    it.id
                                } ?: 0) + 1

                            movimentacoes.add(
                                novaMovimentacao.copy(id = novoId)
                            )

                            telaAtual = "historico"
                        },
                        onCancelar = {
                            telaAtual = "dashboard"
                        }
                    )
                }

                "relatorios" -> {
                    RelatoriosScreen(movimentacoes)
                }
            }
        }

        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Verde
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Sair da conta")
        }
    }
}
