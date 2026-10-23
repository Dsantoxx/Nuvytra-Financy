
package com.example.myfinance.metas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

private val VerdeMeta = Color(0xFF145A3A)
private val FundoMeta = Color(0xFFF5F8F6)

data class MetaFinanceira(
    val id: Int,
    val nome: String,
    val objetivo: Double,
    val acumulado: Double
)

@Composable
fun MetaScreen() {
    val metas = remember {
        mutableStateListOf<MetaFinanceira>()
    }

    var mostrarFormulario by remember { mutableStateOf(false) }
    var metaEditando by remember { mutableStateOf<MetaFinanceira?>(null) }
    var metaDepositando by remember { mutableStateOf<MetaFinanceira?>(null) }

    var nome by remember { mutableStateOf("") }
    var objetivo by remember { mutableStateOf("") }
    var acumulado by remember { mutableStateOf("") }
    var deposito by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }

    val moeda = remember {
        NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    }

    fun converterValor(texto: String): Double? {
        val numero = texto.trim()
            .replace(".", "")
            .replace(",", ".")
            .toDoubleOrNull()

        return numero?.takeIf { it.isFinite() && it >= 0.0 }
    }

    fun abrirFormulario(meta: MetaFinanceira? = null) {
        metaEditando = meta
        nome = meta?.nome ?: ""
        objetivo = meta?.objetivo?.toString() ?: ""
        acumulado = meta?.acumulado?.toString() ?: ""
        erro = ""
        mostrarFormulario = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoMeta)
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Metas financeiras",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = VerdeMeta
        )

        Text(
            text = "Transforme seus planos em conquistas.",
            color = Color.Gray
        )

        Button(
            onClick = { abrirFormulario() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = VerdeMeta
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("+ Criar nova meta")
        }

        if (metas.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(22.dp)
                ) {
                    Text(
                        "Nenhuma meta cadastrada",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Crie sua primeira meta para começar a economizar.",
                        color = Color.Gray
                    )
                }
            }
        }

        metas.forEach { meta ->
            val progresso = if (meta.objetivo > 0) {
                (meta.acumulado / meta.objetivo)
                    .toFloat()
                    .coerceIn(0f, 1f)
            } else 0f

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        meta.nome,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = VerdeMeta
                    )

                    Text(
                        "${moeda.format(meta.acumulado)} de " +
                                moeda.format(meta.objetivo)
                    )

                    LinearProgressIndicator(
                        progress = { progresso },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp),
                        color = VerdeMeta,
                        trackColor = Color(0xFFE3F0E8)
                    )

                    Text(
                        "${(progresso * 100).toInt()}% concluído",
                        color = VerdeMeta,
                        fontWeight = FontWeight.Medium
                    )

                    if (meta.acumulado >= meta.objetivo) {
                        Text(
                            "Meta alcançada! Parabéns!",
                            color = VerdeMeta,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = {
                            metaDepositando = meta
                            deposito = ""
                            erro = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VerdeMeta
                        )
                    ) {
                        Text("Adicionar dinheiro")
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { abrirFormulario(meta) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Editar")
                        }

                        OutlinedButton(
                            onClick = {
                                metas.removeAll { it.id == meta.id }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Excluir")
                        }
                    }
                }
            }
        }
    }

    if (mostrarFormulario) {
        AlertDialog(
            onDismissRequest = { mostrarFormulario = false },
            title = {
                Text(
                    if (metaEditando == null)
                        "Criar meta"
                    else
                        "Editar meta"
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Nome da meta") },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = objetivo,
                        onValueChange = { objetivo = it },
                        label = { Text("Valor desejado (R$)") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = acumulado,
                        onValueChange = { acumulado = it },
                        label = { Text("Valor já economizado (R$)") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        singleLine = true
                    )

                    if (erro.isNotEmpty()) {
                        Text(erro, color = Color.Red)
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val valorObjetivo = converterValor(objetivo)
                        val valorAcumulado = converterValor(acumulado)

                        if (
                            nome.isBlank() ||
                            valorObjetivo == null ||
                            valorObjetivo <= 0 ||
                            valorAcumulado == null
                        ) {
                            erro = "Preencha os campos corretamente."
                        } else {
                            val atual = metaEditando

                            if (atual == null) {
                                val id = (metas.maxOfOrNull {
                                    it.id
                                } ?: 0) + 1

                                metas.add(
                                    MetaFinanceira(
                                        id,
                                        nome.trim(),
                                        valorObjetivo,
                                        valorAcumulado
                                    )
                                )
                            } else {
                                val indice = metas.indexOfFirst {
                                    it.id == atual.id
                                }

                                if (indice >= 0) {
                                    metas[indice] = atual.copy(
                                        nome = nome.trim(),
                                        objetivo = valorObjetivo,
                                        acumulado = valorAcumulado
                                    )
                                }
                            }

                            mostrarFormulario = false
                        }
                    }
                ) {
                    Text("Salvar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { mostrarFormulario = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    metaDepositando?.let { meta ->
        AlertDialog(
            onDismissRequest = { metaDepositando = null },
            title = { Text("Adicionar dinheiro") },
            text = {
                Column {
                    Text("Meta: ${meta.nome}")

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = deposito,
                        onValueChange = { deposito = it },
                        label = { Text("Valor (R$)") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        singleLine = true
                    )

                    if (erro.isNotEmpty()) {
                        Text(erro, color = Color.Red)
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val valor = converterValor(deposito)

                        if (valor == null || valor <= 0) {
                            erro = "Digite um valor maior que zero."
                        } else {
                            val indice = metas.indexOfFirst {
                                it.id == meta.id
                            }

                            if (indice >= 0) {
                                metas[indice] = meta.copy(
                                    acumulado = meta.acumulado + valor
                                )
                            }

                            metaDepositando = null
                        }
                    }
                ) {
                    Text("Adicionar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { metaDepositando = null }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
