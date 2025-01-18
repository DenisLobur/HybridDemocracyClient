package com.example.hybriddemocracy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.hybriddemocracy.R
import com.example.hybriddemocracy.ui.screens.detail.Detail
import com.example.hybriddemocracy.ui.screens.home.Home
import com.example.hybriddemocracy.ui.screens.login.Login

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController, startDestination = Screen.Login.route) { //Screen.Home.route.plus("/asd@qwe.com")) { // Revert to Login.route
        composable(Screen.Login.route) {
            Login(
                navController = navController,
            )
        }
        composable(
            Screen.Home.route.plus(Screen.Home.objectPath),
            arguments = listOf(navArgument(Screen.Home.objectName) {
                type = NavType.StringType
            })
        ) {
            val email = it.arguments?.getString(Screen.Home.objectName)
            email?.let {
                Home(
                    navController = navController,
                    email = email
                )
            }
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

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        Screen.Login.route -> stringResource(id = R.string.login)
        Screen.Home.route -> stringResource(id = R.string.home)
        Screen.Detail.route -> stringResource(id = R.string.detail)
        else -> {
            stringResource(R.string.app_name)
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}