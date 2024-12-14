package com.example.hybriddemocracy.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController, modifier: Modifier = Modifier) {
    val bills = listOf<String>("Bill 1", "Bill 2", "Bill 3", "Bill 4", "Bill 5")

    LazyColumn {
        items(items = bills) { bill ->
            Text(
                text = bill,
                modifier = Modifier
                    .clickable {
                        navController.navigate("detail/$bill")
                    }
                    .padding(16.dp)
            )
        }
    }
}