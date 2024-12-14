package com.example.hybriddemocracy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hybriddemocracy.composables.DetailScreen
import com.example.hybriddemocracy.composables.LoginScreen
import com.example.hybriddemocracy.composables.MainScreen
import com.example.hybriddemocracy.ui.theme.HybridDemocracyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HybridDemocracyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("main") { MainScreen(navController) }
        composable(
            "detail/{billName}",
            arguments = listOf(navArgument("billName") { type = NavType.StringType })
        ) { backStackEntry ->
            DetailScreen(
                navController = navController,
                billName = backStackEntry.arguments?.getString("billName") ?: ""
            )
        }
    }
}
