package com.example.finalproject.presentation.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.finalproject.presentation.ui.screen.login.LoginScreen
import com.example.finalproject.presentation.ui.screen.profile.ProfileScreen
import com.example.finalproject.presentation.ui.screen.profile.ProfileScreenCallbacks
import com.example.finalproject.presentation.ui.screen.registration.RegistrationScreen
import com.example.finalproject.presentation.ui.screen.settings.PreferencesScreen
import com.example.finalproject.presentation.ui.screen.settings.SettingsScreen
import com.example.finalproject.presentation.ui.screen.settings.account.AccountScreen
import com.example.finalproject.presentation.ui.theme.TransparentColor
import kotlinx.serialization.Serializable

@Serializable
data object LoginScreenDestination
@Serializable
data object RegistrationScreenDestination
@Serializable
data object HomeScreenDestination
@Serializable
data object ProfileScreenDestination
@Serializable
data object SettingsScreenDestination
@Serializable
data object AccountScreenDestination
@Serializable
data object PreferencesScreenDestination
@Serializable
data object ThemeScreenDestination

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Any
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        containerColor = TransparentColor,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if(currentRoute in listOf(
                ProfileScreenDestination::class.qualifiedName,
                HomeScreenDestination::class.qualifiedName,
                SettingsScreenDestination::class.qualifiedName,
                AccountScreenDestination::class.qualifiedName,
                PreferencesScreenDestination::class.qualifiedName
            )) {
                BottomNavBar(navController)
            }
        }
    ) { padding ->

        NavHost(navController = navController,
            startDestination = when (startDestination) {
                "profile" -> ProfileScreenDestination
                else -> LoginScreenDestination},
            modifier = Modifier.padding(padding))
        {
            composable<LoginScreenDestination> {
                LoginScreen(navigateToRegister = {
                    navController.navigate(RegistrationScreenDestination)
                },
                    navigateToProfile = {
                        navController.navigate(ProfileScreenDestination){
                            popUpTo(LoginScreenDestination) { inclusive = true }
                        }
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
                        showSettingsDialog = {navController.navigate(SettingsScreenDestination)},
                        showHelpDialog = {},
                        showFeedbackDialog = {}
                    )
                )
            }

            composable<SettingsScreenDestination> {

                SettingsScreen(
                    navigateToAccount = {navController.navigate(AccountScreenDestination)},
                    navigateToPreferences = {navController.navigate(PreferencesScreenDestination)},
                    navigateToThemes = {navController.navigate(ThemeScreenDestination)},
                    navigateBack = {navController.popBackStack()}
                )
            }

            composable<AccountScreenDestination> {
                AccountScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToLogin = { navController.navigate(LoginScreenDestination)}
                )
            }

            composable<PreferencesScreenDestination> {
                PreferencesScreen(navigateBack = { navController.popBackStack() })
            }

//        composable<ThemeScreenDestination> {
//            ThemeScreen(navigateBack = { navController.popBackStack() })
//        }
        }
    }

}