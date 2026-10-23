
package com.example.myfinance.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfinance.model.Movimentacao
import com.example.myfinance.utils.*

@Composable
fun CardMovimentacao(item: Movimentacao) {

    val corValor = if (item.tipo == "Receita") {
        VerdeDestaque
    } else {
        Vermelho
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.descricao,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = item.categoria,
                fontSize = 13.sp,
                color = TextoSecundario
            )

            Text(
                text = item.data,
                fontSize = 12.sp,
                color = TextoSecundario
            )
        }

        Text(
            text = (if (item.tipo == "Receita") "+ " else "- ") +
                    formatarDinheiro(item.valor),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = corValor
        )
    }
}
