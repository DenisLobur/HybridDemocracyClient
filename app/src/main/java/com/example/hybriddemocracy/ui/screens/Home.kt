package com.example.hybriddemocracy.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hybriddemocracy.composables.views.BillItem
import kotlin.random.Random

@Composable
fun Home(navController: NavController, modifier: Modifier = Modifier) {

    Column() {
        Text(
            text = "Please select a bill and vote".uppercase(),
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            ),
            modifier = modifier.padding(top = 80.dp).padding(horizontal = 20.dp)
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
                        .padding(16.dp)
                )
            }
        }
    }
}

private fun rndNum(): Int = Random.nextInt(100000, 1000000)

