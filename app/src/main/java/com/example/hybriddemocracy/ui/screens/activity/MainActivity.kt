package com.example.hybriddemocracy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
//import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.activity.viewModels
import com.example.hybriddemocracy.ui.screens.Login
import com.example.hybriddemocracy.ui.screens.activity.MainActivityViewModel
import com.example.hybriddemocracy.ui.theme.HybridDemocracyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        installSplashScreen().apply {
//            setKeepOnScreenCondition { splashViewModel.isLoading.value }
//        }
        setContent {
            HybridDemocracyTheme {
            Login()
            //MyApp()
            }
        }
    }
}

//@Composable
//fun MyApp() {
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = "login") {
//        composable("login") { Login(navController) }
//        composable("main") { Home(navController) }
//        composable(
//            "detail/{billName}",
//            arguments = listOf(navArgument("billName") { type = NavType.StringType })
//        ) { backStackEntry ->
//            Detail(
//                navController = navController,
//                billName = backStackEntry.arguments?.getString("billName") ?: ""
//            )
//        }
//    }
//}
