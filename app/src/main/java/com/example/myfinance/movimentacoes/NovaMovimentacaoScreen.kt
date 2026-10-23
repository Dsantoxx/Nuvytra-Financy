
package com.example.myfinance.movimentacoes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfinance.model.Movimentacao
import com.example.myfinance.ui.theme.Verde
import com.example.myfinance.ui.theme.Fundo
import com.example.myfinance.ui.theme.TextoPrincipal
import com.example.myfinance.ui.theme.TextoSecundario
import com.example.myfinance.ui.theme.Vermelho
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NovaMovimentacaoScreen(
    onSalvar: (Movimentacao) -> Unit,
    onCancelar: () -> Unit
) {
    var descricao by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("Despesa") }
    var erro by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fundo)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Nova movimentação",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Verde
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Registre suas receitas e despesas.",
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Tipo de movimentação",
            fontWeight = FontWeight.SemiBold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listOf("Receita", "Despesa").forEach { opcao ->
                FilterChip(
                    selected = tipo == opcao,
                    onClick = { tipo = opcao },
                    label = { Text(opcao) }
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição") },
            placeholder = { Text("Ex: Supermercado") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = valor,
            onValueChange = { valor = it },
            label = { Text("Valor (R$)") },
            placeholder = { Text("Ex: 150,00") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = categoria,
            onValueChange = { categoria = it },
            label = { Text("Categoria") },
            placeholder = { Text("Ex: Alimentação") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (erro.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = erro,
                color = Vermelho
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                val valorNumerico = valor
                    .trim()
                    .replace(".", "")
                    .replace(",", ".")
                    .toDoubleOrNull()

                if (
                    descricao.isBlank() ||
                    categoria.isBlank() ||
                    valorNumerico == null ||
                    !valorNumerico.isFinite() ||
                    valorNumerico <= 0
                ) {
                    erro = "Preencha todos os campos corretamente."
                } else {
                    erro = ""

                    val dataAtual = SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale("pt", "BR")
                    ).format(Date())

                    onSalvar(
                        Movimentacao(
                            id = 0,
                            descricao = descricao.trim(),
                            categoria = categoria.trim(),
                            valor = valorNumerico,
                            tipo = tipo,
                            data = dataAtual
                        )
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Verde
            )
        ) {
            Text(
                text = "Salvar movimentação",
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onCancelar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}
