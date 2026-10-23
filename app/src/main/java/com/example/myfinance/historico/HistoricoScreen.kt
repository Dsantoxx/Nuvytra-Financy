
package com.example.myfinance.historico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfinance.components.CardMovimentacao
import com.example.myfinance.model.Movimentacao
import com.example.myfinance.utils.*

@Composable
fun HistoricoScreen(
    movimentacoes: SnapshotStateList<Movimentacao>
) {
    var filtro by remember { mutableStateOf("Todas") }
    var editando by remember {
        mutableStateOf<Movimentacao?>(null)
    }
    var excluindo by remember {
        mutableStateOf<Movimentacao?>(null)
    }

    val lista = movimentacoes.filter {
        filtro == "Todas" || it.tipo == filtro
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fundo)
            .padding(20.dp)
    ) {
        Text(
            "Histórico financeiro",
            fontSize = 25.sp,
            color = TextoPrincipal,
            fontWeight = FontWeight.Bold
        )

        Text(
            "Acompanhe todas as suas movimentações.",
            color = TextoSecundario,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Todas", "Receita", "Despesa").forEach { opcao ->
                FilterChip(
                    selected = filtro == opcao,
                    onClick = { filtro = opcao },
                    label = { Text(opcao) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Verde,
                        selectedLabelColor = Branco,
                        containerColor = Branco,
                        labelColor = Verde
                    )
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        if (lista.isEmpty()) {
            Text(
                "Nenhuma movimentação encontrada.",
                color = TextoSecundario
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(lista, key = { it.id }) { item ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Branco
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        CardMovimentacao(item)

                        Row {
                            TextButton(
                                onClick = { editando = item }
                            ) {
                                Text("Editar", color = Verde)
                            }

                            TextButton(
                                onClick = { excluindo = item }
                            ) {
                                Text("Excluir", color = Vermelho)
                            }
                        }
                    }
                }
            }
        }
    }

    excluindo?.let { item ->
        AlertDialog(
            onDismissRequest = { excluindo = null },
            title = { Text("Excluir movimentação") },
            text = {
                Text("Deseja excluir ${item.descricao}?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        movimentacoes.removeAll {
                            it.id == item.id
                        }
                        excluindo = null
                    }
                ) {
                    Text("Excluir", color = Vermelho)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { excluindo = null }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    editando?.let { item ->
        var descricao by remember(item.id) {
            mutableStateOf(item.descricao)
        }

        var valor by remember(item.id) {
            mutableStateOf(item.valor.toString())
        }

        val novoValor = valor.replace(",", ".").toDoubleOrNull()
        val valido = descricao.isNotBlank() &&
                novoValor != null && novoValor > 0

        AlertDialog(
            onDismissRequest = { editando = null },
            title = { Text("Editar movimentação") },
            text = {
                Column {
                    OutlinedTextField(
                        value = descricao,
                        onValueChange = { descricao = it },
                        label = { Text("Descrição") }
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = valor,
                        onValueChange = { valor = it },
                        label = { Text("Valor") }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = valido,
                    onClick = {
                        val indice = movimentacoes.indexOfFirst {
                            it.id == item.id
                        }

                        if (indice >= 0 && novoValor != null) {
                            movimentacoes[indice] = item.copy(
                                descricao = descricao,
                                valor = novoValor
                            )
                        }

                        editando = null
                    }
                ) {
                    Text("Salvar", color = Verde)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { editando = null }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}


