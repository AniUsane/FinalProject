package com.example.finalproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.datastore.dataStore
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.presentation.ui.screen.login.LoginScreen
import com.example.finalproject.presentation.ui.screen.profile.ProfileScreen
import com.example.finalproject.presentation.ui.screen.profile.ProfileScreenCallbacks
import com.example.finalproject.presentation.ui.screen.registration.RegistrationScreen
import kotlinx.serialization.Serializable

@Serializable
data object LoginScreenDestination
@Serializable
data object RegistrationScreenDestination
@Serializable
data object HomeScreenDestination
@Serializable
data object ProfileScreenDestination

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String
){
    NavHost(navController = navController, startDestination = when (startDestination) {
        "profile" -> ProfileScreenDestination
        else -> LoginScreenDestination
    }) {
        composable<LoginScreenDestination> {
            LoginScreen(navigateToRegister = {
                navController.navigate(RegistrationScreenDestination)
            },
                navigateToProfile = {
                    navController.navigate(ProfileScreenDestination)
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

        composable<ProfileScreenDestination> {
            ProfileScreen(
                callbacks = ProfileScreenCallbacks(
                    navigateToLogin = {
                        navController.navigate(LoginScreenDestination) {
                            popUpTo(ProfileScreenDestination) { inclusive = true }
                        }
                    },
                    showSnackBar = {},
                    showSettingsDialog = {},
                    showHelpDialog = {},
                    showFeedbackDialog = {}
                )
            )
        }
    }
}