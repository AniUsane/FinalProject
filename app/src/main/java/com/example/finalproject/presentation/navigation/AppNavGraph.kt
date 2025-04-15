package com.example.finalproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.presentation.ui.screen.auth.login.LoginScreen
import com.example.finalproject.presentation.ui.screen.auth.registration.RegistrationScreen
import kotlinx.serialization.Serializable

@Serializable
data object LoginScreenDestination
@Serializable
data object RegistrationScreenDestination


@Composable
fun AppNavGraph(onAuthSuccess: () -> Unit){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = RegistrationScreenDestination) {
        composable<LoginScreenDestination> {
            LoginScreen(navigateToRegister = {
                navController.navigate(RegistrationScreenDestination)
            },
                navigateToHome = {
                    onAuthSuccess()
                })
        }

        composable<RegistrationScreenDestination> {
            RegistrationScreen(navigateToLogin = {
                navController.navigate(LoginScreenDestination)
            })
        }

    }
}

@Composable
fun RootNavigationGraph(
    isAuthenticated: Boolean,
    onAuthSuccess: () -> Unit
) {
    if (isAuthenticated) {
        MyBottomAppBar()
    } else {
        AppNavGraph(onAuthSuccess = onAuthSuccess)
    }
}