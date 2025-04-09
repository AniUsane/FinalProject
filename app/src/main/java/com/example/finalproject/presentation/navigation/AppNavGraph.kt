package com.example.finalproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.presentation.ui.screen.login.LoginScreen
import com.example.finalproject.presentation.ui.screen.registration.RegistrationScreen
import kotlinx.serialization.Serializable

@Serializable
data object LoginScreenDestination
@Serializable
data object RegistrationScreenDestination
@Serializable
data object HomeScreenDestination

@Composable
fun AppNavGraph(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = RegistrationScreenDestination) {
        composable<LoginScreenDestination> {
            LoginScreen(navigateToRegister = {
                navController.navigate(RegistrationScreenDestination)
            },
                navigateToHome = {
                    navController.navigate(HomeScreenDestination)
                })
        }

        composable<RegistrationScreenDestination> {
            RegistrationScreen(navigateToLogin = {
                navController.navigate(LoginScreenDestination)
            })
        }

        composable<HomeScreenDestination>{
            androidx.compose.material3.Text("Welcome to Home!")
        }
    }
}