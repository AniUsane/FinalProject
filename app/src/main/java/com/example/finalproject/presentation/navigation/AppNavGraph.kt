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
import com.example.finalproject.presentation.ui.screen.auth.login.LoginScreen
import com.example.finalproject.presentation.ui.screen.bookHotel.BookHotelScreen
import com.example.finalproject.presentation.ui.screen.bookHotel.calendar.ChooseDateScreen
import com.example.finalproject.presentation.ui.screen.bookHotel.citySearch.CitySearchScreen
import com.example.finalproject.presentation.ui.screen.bookHotel.travelers.TravelersScreen
import com.example.finalproject.presentation.ui.screen.home.Home
import com.example.finalproject.presentation.ui.screen.profile.ProfileScreen
import com.example.finalproject.presentation.ui.screen.profile.ProfileScreenCallbacks
import com.example.finalproject.presentation.ui.screen.registration.RegistrationScreen
import com.example.finalproject.presentation.ui.screen.settings.PreferencesScreen
import com.example.finalproject.presentation.ui.screen.settings.SettingsScreen
import com.example.finalproject.presentation.ui.screen.settings.account.AccountScreen
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
data object ThemeScreenDestination
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
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

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
                BottomNavBar(navController)
            }
        }
    ) { padding ->

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

            composable<BookHotelScreenDestination> {
                val savedStateHandle = it.savedStateHandle
                BookHotelScreen(
                    navigateToCitySearch = { navController.navigate(CitySearchScreenDestination) },
                    savedStateHandle = savedStateHandle,
                    navigateToChooseDate = { navController.navigate(ChooseDateScreenDestination)},
                    navigateToTravelers = {navController.navigate(TravelersScreenDestination)}
                )
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

//        composable<ThemeScreenDestination> {
//            ThemeScreen(navigateBack = { navController.popBackStack() })
//        }
        }
    }

}