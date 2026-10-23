
package com.example.myfinance.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val VerdePerfil = Color(0xFF145A3A)
private val FundoPerfil = Color(0xFFF5F8F6)

@Composable
fun PerfilScreen(
    onLogout: () -> Unit
) {
    var nome by remember { mutableStateOf("Usuário Nuvytra") }
    var email by remember { mutableStateOf("usuario@exemplo.com") }

    var editarPerfil by remember { mutableStateOf(false) }
    var nomeEditado by remember { mutableStateOf(nome) }
    var emailEditado by remember { mutableStateOf(email) }
    var erro by remember { mutableStateOf("") }

    var notificacoes by remember { mutableStateOf(true) }
    var confirmarSaida by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoPerfil)
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Perfil e configurações",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = VerdePerfil
        )

        Text(
            text = "Gerencie suas informações e preferências.",
            color = Color.Gray
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Meu perfil",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = VerdePerfil
                )

                Text("Nome: $nome")
                Text("E-mail: $email")

                Button(
                    onClick = {
                        nomeEditado = nome
                        emailEditado = email
                        erro = ""
                        editarPerfil = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VerdePerfil
                    )
                ) {
                    Text("Editar perfil")
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Preferências",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = VerdePerfil
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Notificações",
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Ativar preferência de notificações",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Switch(
                        checked = notificacoes,
                        onCheckedChange = {
                            notificacoes = it
                        }
                    )
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Sobre o aplicativo",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = VerdePerfil
                )

                Text("Nuvytra Financy")
                Text("Versão 1.0")
                Text(
                    text = "Aplicativo de controle financeiro " +
                            "pessoal, desenvolvido em Kotlin " +
                            "com Jetpack Compose.",
                    color = Color.Gray
                )
            }
        }

        OutlinedButton(
            onClick = { confirmarSaida = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sair da conta", color = VerdePerfil)
        }
    }

    if (editarPerfil) {
        AlertDialog(
            onDismissRequest = {
                editarPerfil = false
            },
            title = {
                Text("Editar perfil")
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = nomeEditado,
                        onValueChange = {
                            nomeEditado = it
                        },
                        label = { Text("Nome") },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = emailEditado,
                        onValueChange = {
                            emailEditado = it
                        },
                        label = { Text("E-mail") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
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
                        if (
                            nomeEditado.isBlank() ||
                            !android.util.Patterns.EMAIL_ADDRESS
                                .matcher(emailEditado.trim())
                                .matches()
                        ) {
                            erro = "Informe um nome e e-mail válidos."
                        } else {
                            nome = nomeEditado.trim()
                            email = emailEditado.trim()
                            editarPerfil = false
                        }
                    }
                ) {
                    Text("Salvar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { editarPerfil = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (confirmarSaida) {
        AlertDialog(
            onDismissRequest = {
                confirmarSaida = false
            },
            title = {
                Text("Sair da conta?")
            },
            text = {
                Text("Deseja realmente encerrar sua sessão?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        confirmarSaida = false
                        onLogout()
                    }
                ) {
                    Text("Sair")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { confirmarSaida = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
