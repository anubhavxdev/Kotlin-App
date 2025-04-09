package com.example.awslpu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.alpha
import com.example.myapplication.R

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit = { _, _ -> },
    onForgotPasswordClick: () -> Unit = {}
) {
    val amazonOrange = Color(0xFFFF9900)
    val amazonBlack = Color(0xFF232F3E)
    val amazonDarkBlue = Color(0xFF37475A)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(amazonBlack)
    ) {
        // AWS Logo in the background
        Image(
            painter = painterResource(id = R.drawable.aws_logo),
            contentDescription = "AWS Logo",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .alpha(0.05f)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineMedium,
                color = amazonOrange
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = amazonOrange,
                    focusedLabelColor = amazonOrange,
                    cursorColor = amazonOrange
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = amazonOrange,
                    focusedLabelColor = amazonOrange,
                    cursorColor = amazonOrange
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onForgotPasswordClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Forgot Password?", color = amazonOrange)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onLoginClick(email, password) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = amazonOrange)
            ) {
                Text("Login", color = Color.White)
            }
        }
    }
}
