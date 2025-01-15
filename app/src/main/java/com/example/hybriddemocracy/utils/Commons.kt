package com.example.hybriddemocracy.utils

import androidx.navigation.NavController

fun NavController.singleTopNavigator(route: String) {
    this.navigate(route) {
        graph.startDestinationRoute?.let { route ->
            popUpTo(route) {
                inclusive = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}