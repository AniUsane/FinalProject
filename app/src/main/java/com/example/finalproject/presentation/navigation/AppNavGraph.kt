package com.example.finalproject.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.finalproject.presentation.ui.screen.auth.login.LoginScreen
import com.example.finalproject.presentation.ui.screen.bookHotel.calendar.ChooseDateScreen
import com.example.finalproject.presentation.ui.screen.bookHotel.citySearch.CitySearchScreen
import com.example.finalproject.presentation.ui.screen.bookHotel.travelers.TravelersScreen
import com.example.finalproject.presentation.ui.screen.home.Home
import com.example.finalproject.presentation.ui.screen.registration.RegistrationScreen
import com.example.finalproject.presentation.ui.screen.settings.PreferencesScreen
import com.example.finalproject.presentation.ui.theme.TransparentColor
import kotlinx.serialization.Serializable
import java.time.LocalDate

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
data object BookHotelScreenDestination
@Serializable
data object CitySearchScreenDestination
@Serializable
data object ChooseDateScreenDestination
@Serializable
data object TravelersScreenDestination
@Serializable
data object AddGuideScreenDestination
@Serializable
data object AddTripScreenDestination

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Any
){
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        containerColor = TransparentColor,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if(currentRoute in listOf(
                    ProfileScreenDestination::class.qualifiedName,
                    HomeScreenDestination::class.qualifiedName,
                    PreferencesScreenDestination::class.qualifiedName,
                    BookHotelScreenDestination::class.qualifiedName
                )) {
                BottomNavBar()
            }
        }
    ) { padding ->
        Log.d("AppNavGraph", "Current route: $currentRoute")
        NavHost(navController = navController,
            startDestination = when (startDestination) {
                "home" -> HomeScreenDestination
                else -> LoginScreenDestination},
            modifier = Modifier.padding(padding))
        {
            composable<LoginScreenDestination> {
                LoginScreen(navigateToRegister = {
                    navController.navigate(RegistrationScreenDestination)
                },
                    navigateToHome = {
                        navController.navigate(HomeScreenDestination){
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
                Home()
            }

            composable<PreferencesScreenDestination> {
                PreferencesScreen(navigateBack = { navController.popBackStack() })
            }

            composable<CitySearchScreenDestination> {
                CitySearchScreen(
                    onCitySelected = { city ->
                        navController.previousBackStackEntry?.savedStateHandle?.set("city_name", city.name)
                        navController.previousBackStackEntry?.savedStateHandle?.set("city_country", city.countryName)

                        navController.popBackStack()
                    },
                    onBackPressed = { navController.popBackStack() }
                )
            }

            composable<ChooseDateScreenDestination> {
                ChooseDateScreen(
                    startDate = it.savedStateHandle.get<LocalDate>("check_in"),
                    endDate = it.savedStateHandle.get<LocalDate>("check_out"),
                    onDoneClicked = { (startDate, endDate) ->
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("check_in", startDate)

                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("check_out", endDate)

                        navController.popBackStack()
                    },
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }

            composable<TravelersScreenDestination> {
                TravelersScreen(
                    onSaveTravelers = { travelers ->
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("travelers", travelers)
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
