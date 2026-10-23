
package com.example.myfinance.login

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.myfinance.R
import com.example.myfinance.utils.*

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mostrarSenha by remember { mutableStateOf(false) }
    var erro by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fundo)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        // Logo oficial Nuvytra Financy
        Image(
            painter = painterResource(
                id = R.drawable.logo_nuvytra
            ),
            contentDescription = "Logo Nuvytra Financy",
            modifier = Modifier.size(130.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Nuvytra Financy",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Verde
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Seu dinheiro, seu controle.",
            fontSize = 14.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(45.dp))

        Text(
            text = "Bem-vindo de volta!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Entre na sua conta para continuar.",
            fontSize = 14.sp,
            color = TextoSecundario,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(25.dp))

        // Campo de e-mail
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                erro = ""
            },
            label = {
                Text("E-mail")
            },
            placeholder = {
                Text("seuemail@exemplo.com")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Verde,
                focusedLabelColor = Verde,
                cursorColor = Verde
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Campo de senha
        OutlinedTextField(
            value = senha,
            onValueChange = {
                senha = it
                erro = ""
            },
            label = {
                Text("Senha")
            },
            singleLine = true,
            visualTransformation = if (mostrarSenha) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                TextButton(
                    onClick = {
                        mostrarSenha = !mostrarSenha
                    }
                ) {
                    Text(
                        text = if (mostrarSenha) {
                            "Ocultar"
                        } else {
                            "Mostrar"
                        },
                        color = Verde,
                        fontSize = 12.sp
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Verde,
                focusedLabelColor = Verde,
                cursorColor = Verde
            )
        )

        // Mensagem de erro
        if (erro.isNotEmpty()) {

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = erro,
                color = Vermelho,
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Botão de entrar
        Button(
            onClick = {
                if (email.isBlank() || senha.isBlank()) {
                    erro = "Preencha o e-mail e a senha."
                } else {
                    onLoginSuccess()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Verde,
                contentColor = Branco
            )
        ) {
            Text(
                text = "Entrar",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Organize suas finanças com tranquilidade.",
            fontSize = 12.sp,
            color = TextoSecundario,
            textAlign = TextAlign.Center
        )
    }
}
