package com.example.hybriddemocracy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.hybriddemocracy.ui.screens.Detail
import com.example.hybriddemocracy.ui.screens.Home
import com.example.hybriddemocracy.ui.screens.login.Login

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            Login()
        }
        composable(Screen.Home.route) {
            Home(
                navController = navController
            )
        }
        composable(
            Screen.Detail.route.plus(Screen.Detail.objectPath),
            arguments = listOf(navArgument(Screen.Detail.objectName) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            Detail(
                navController = navController,
                billName = backStackEntry.arguments?.getString("billName") ?: ""
            )
        }
    }
}