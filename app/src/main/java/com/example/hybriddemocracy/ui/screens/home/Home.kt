package com.example.hybriddemocracy.ui.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hybriddemocracy.composables.views.BillItem
import kotlin.random.Random

@Composable
fun Home(navController: NavController, modifier: Modifier = Modifier, email: String) {

    val userModel: UserViewModel = hiltViewModel<UserViewModel>()
    val isLoading by userModel.isLoading.collectAsState()
    val user by userModel.user.collectAsState()

    Log.d("denys", "email: $email")

    LaunchedEffect(Unit) {
        userModel.getUserByEmail(email) {
            // Handle the user data
            Log.d("denys", "user: $user")
        }
    }

    Column() {
        Text(
            text = "Hello, "  + (user?.name ?: "User").uppercase(),
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            ),
            modifier = modifier.padding(top = 20.dp).padding(horizontal = 20.dp)
        )

        Text(
            text = "Please select a bill and vote".uppercase(),
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            ),
            modifier = modifier.padding(top = 20.dp).padding(horizontal = 20.dp)
        )

        val bills = listOf("Bill No. ${rndNum()}", "Bill No. ${rndNum()}", "Bill No. ${rndNum()}", "Bill No. ${rndNum()}", "Bill No. ${rndNum()}", "Bill No. ${rndNum()}")

        LazyColumn {
            items(items = bills) { bill ->
                BillItem(
                    text = bill,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("detail/$bill")
//                            viewModel.getUserById("user_id") { user ->
                                // Handle the user data
//                            }
                        }
                        .padding(20.dp)
                )
            }
        }
    }
}

private fun rndNum(): Int = Random.nextInt(100000, 1000000)

