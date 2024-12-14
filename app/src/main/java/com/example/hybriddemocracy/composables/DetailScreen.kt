package com.example.hybriddemocracy.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun DetailScreen(navController: NavController, billName: String, modifier: Modifier = Modifier) {
    Text(text = "Details of $billName", modifier = modifier)
}