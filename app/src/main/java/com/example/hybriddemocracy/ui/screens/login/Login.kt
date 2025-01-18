package com.example.hybriddemocracy.ui.screens.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hybriddemocracy.R
import com.example.hybriddemocracy.navigation.Screen
import com.example.hybriddemocracy.navigation.currentRoute

@Composable
fun Login(
    navController: NavController
) {
    val viewModel: LoginViewModel = hiltViewModel<LoginViewModel>()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var emailText by remember { mutableStateOf("") }
        var passwordText by remember { mutableStateOf("") }

        val activeScreen = currentRoute(navController)

        Text(
            text = "Welcome to Digital Democracy".uppercase(),
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(vertical = 80.dp)
        )

        OutlinedTextField(
            value = emailText,
            onValueChange = { newText ->
                emailText = newText
            },
            placeholder = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)
                .border(
                    BorderStroke(1.dp, colorResource(id = R.color.purple_700)),
                    RoundedCornerShape(4.dp)
                )
        )

        OutlinedTextField(
            value = passwordText,
            onValueChange = { newText ->
                passwordText = newText
            },
            placeholder = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)
                .border(
                    BorderStroke(1.dp, colorResource(id = R.color.purple_700)),
                    RoundedCornerShape(4.dp)
                )
        )

        Button(
            onClick = {
                viewModel.authorize(email = emailText.trim(), password = passwordText.trim()) {
                    navController.navigate(Screen.Home.route.plus("/$emailText")) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple_700)),
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(text = "Login".uppercase())
        }

        Text(
            text = "Don't have account? Register via:".uppercase(),
            style = TextStyle(
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(vertical = 20.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.diia),
            contentDescription = "Diia",
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    //viewModel.sayHello()
                }
        )
    }
}