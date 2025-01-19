package com.example.hybriddemocracy.ui.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hybriddemocracy.R
import com.example.hybriddemocracy.composables.views.BillItem
import com.example.hybriddemocracy.navigation.Screen
import com.example.hybriddemocracy.ui.screens.login.LoginViewModel
import kotlin.random.Random


@Composable
fun Home(navController: NavController, email: String) {

    val userModel: HomeViewModel = hiltViewModel<HomeViewModel>()
    val loginModel: LoginViewModel = hiltViewModel<LoginViewModel>()
    val isLoading by userModel.isLoading.collectAsState()
    val user by userModel.user.collectAsState()
    val bills by userModel.bills.collectAsState()

    LaunchedEffect(email) {
        userModel.getUserByEmail("abc@def.eu") {//email) { //"asd@qwe.com") {
            // Handle the user data
            //Log.d("denys", "user: $user")
        }
    }

    LaunchedEffect(user) {
        user?.let {
            userModel.getBillsByUserId(it.id) {
                //Log.d("denys", "bills: $bills")
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Hello, " + (user?.name ?: "User").uppercase(),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp)
            )

            Text(
                text = "Please select a bill and vote".uppercase(),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp)
            )

//            val bills = listOf("Bill No. ${rndNum()}", "Bill No. ${rndNum()}", "Bill No. ${rndNum()}", "Bill No. ${rndNum()}", "Bill No. ${rndNum()}", "Bill No. ${rndNum()}")
            bills?.let {
                LazyColumn {
                    items(items = it) { bill ->
                        BillItem(
                            bill = bill,
                            modifier = Modifier
                                .clickable {
                                    navController.navigate("detail/${bill.id}")
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

        Button(
            onClick = {
                loginModel.logout()
                navController.navigate(Screen.Login.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple_700)),
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Log out".uppercase())
        }
    }
}

private fun rndNum(): Int = Random.nextInt(100000, 1000000)

