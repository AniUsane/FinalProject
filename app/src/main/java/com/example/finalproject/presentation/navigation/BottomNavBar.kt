package com.example.finalproject.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.R
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
import com.example.finalproject.presentation.ui.screen.trip.TripScreen
import com.example.finalproject.presentation.ui.theme.HorizontalDividerColor
import com.example.finalproject.presentation.ui.theme.Orange
import com.example.finalproject.presentation.ui.theme.SelectedBottomIcon
import com.example.finalproject.presentation.ui.theme.UnselectedBottomIcon
import com.example.finalproject.presentation.ui.theme.White
import java.time.LocalDate


@Composable
fun BottomNavBar() {
    val navigationController = rememberNavController()
    var currentRoute = navigationController.currentBackStackEntryAsState().value?.destination?.route
    val iconHome = ImageVector.vectorResource(id = R.drawable.homefilled)
    val iconBed = ImageVector.vectorResource(id = R.drawable.bed_unselected)
    val iconBuild = Icons.Default.Build
    val iconProfile = ImageVector.vectorResource(id = R.drawable.profile_unselected)


    var showButtons by remember { mutableStateOf(false) } // for FAB buttons displayed

    var rotated by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (rotated) 45f else 0f, // Rotate 180° on click
        animationSpec = tween(durationMillis = 300),
        label = "rotationAnimation"
    )





    Scaffold(
        bottomBar = {
            Column {
                HorizontalDivider(color = HorizontalDividerColor, thickness = 1.dp)
                BottomAppBar(
                    containerColor = White,
                    tonalElevation = 8.dp,
                    modifier = Modifier.height(60.dp)
                ) {
                    //Home icon in navbar
                    IconButton(
                        onClick = {
                            currentRoute = HomeScreenDestination::class.qualifiedName
                            navigationController.navigate(HomeScreenDestination)
                        }, modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            iconHome,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp),
                            tint = if (currentRoute == HomeScreenDestination::class.qualifiedName) SelectedBottomIcon else UnselectedBottomIcon
                        )
                    }


                    //Hotels icon in navbar
                    IconButton(
                        onClick = {
                            currentRoute = BookHotelScreenDestination::class.qualifiedName
                            navigationController.navigate(BookHotelScreenDestination) {}
                        }, modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            iconBed,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp),
                            tint = if (currentRoute == BookHotelScreenDestination::class.qualifiedName) SelectedBottomIcon else UnselectedBottomIcon
                        )
                    }

                    //fab
                    Box(
                        modifier = Modifier
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        FloatingActionButton(
                            onClick = {
                                rotated = !rotated
                                showButtons = rotated

                            },
                            containerColor = Orange,
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .height(42.dp)
                                .width(42.dp)
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .rotate(rotationAngle)
                                    .size(28.dp)
                            )
                        }
                    }


                    //?
                    IconButton(
                        onClick = {
                            currentRoute = BookHotelScreenDestination::class.qualifiedName
                            navigationController.navigate(SettingsScreenDestination)
                        }, modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            iconBuild,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp),
                            tint = if (currentRoute == SettingsScreenDestination::class.qualifiedName) SelectedBottomIcon else UnselectedBottomIcon
                        )
                    }


                    //Profile icon navbar
                    IconButton(
                        onClick = {
                            currentRoute = ProfileScreenDestination::class.qualifiedName
                            navigationController.navigate(ProfileScreenDestination)
                        }, modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            iconProfile,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp),
                            tint = if (currentRoute == ProfileScreenDestination::class.qualifiedName) SelectedBottomIcon else UnselectedBottomIcon
                        )
                    }

                }
            }
        },
    )

    { paddingValues ->

        NavHost(
            navController = navigationController,
            startDestination = HomeScreenDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<HomeScreenDestination> {
                Home()
            }

            composable<BookHotelScreenDestination> {
                val savedStateHandle = it.savedStateHandle
                BookHotelScreen(
                    navigateToCitySearch = { navigationController.navigate(CitySearchScreenDestination) },
                    savedStateHandle = savedStateHandle,
                    navigateToChooseDate = { navigationController.navigate(ChooseDateScreenDestination)},
                    navigateToTravelers = {navigationController.navigate(TravelersScreenDestination)}
                )
            }
            composable<ProfileScreenDestination> {
                ProfileScreen(
                    callbacks = ProfileScreenCallbacks(
                        navigateToLogin = {
                            navigationController.navigate(LoginScreenDestination) {
                                popUpTo(ProfileScreenDestination) { inclusive = true }
                            }
                        },
                        showSnackBar = {},
                        showSettingsDialog = {
                            navigationController.navigate(
                                SettingsScreenDestination
                            )
                        },
                        showHelpDialog = {},
                        showFeedbackDialog = {}
                    )
                )
            }

            composable<SettingsScreenDestination> {

                SettingsScreen(
                    navigateToAccount = { navigationController.navigate(AccountScreenDestination) },
                    navigateToPreferences = {
                        navigationController.navigate(
                            PreferencesScreenDestination
                        )
                    },
                    navigateBack = { navigationController.popBackStack() }
                )
            }

            composable<AccountScreenDestination> {
                AccountScreen(
                    navigateBack = { navigationController.popBackStack() },
                    navigateToLogin = { navigationController.navigate(LoginScreenDestination) }
                )
            }

            composable<PreferencesScreenDestination> {
                PreferencesScreen(navigateBack = { navigationController.popBackStack() })
            }

            composable<CitySearchScreenDestination> {
                CitySearchScreen(
                    onCitySelected = { city ->
                        navigationController.previousBackStackEntry?.savedStateHandle?.set("city_name", city.name)
                        navigationController.previousBackStackEntry?.savedStateHandle?.set("city_country", city.countryName)

                        navigationController.popBackStack()
                    },
                    onBackPressed = { navigationController.popBackStack() }
                )
            }

            composable<ChooseDateScreenDestination> {
                ChooseDateScreen(
                    startDate = it.savedStateHandle.get<LocalDate>("check_in"),
                    endDate = it.savedStateHandle.get<LocalDate>("check_out"),
                    onDoneClicked = { (startDate, endDate) ->
                        navigationController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("check_in", startDate)

                        navigationController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("check_out", endDate)

                        navigationController.popBackStack()
                    },
                    onBackPressed = {
                        navigationController.popBackStack()
                    }
                )
            }

            composable<TravelersScreenDestination> {
                TravelersScreen(
                    onSaveTravelers = { travelers ->
                        navigationController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("travelers", travelers)
                        navigationController.popBackStack()
                    }
                )
            }

            composable<LoginScreenDestination> {
                LoginScreen(navigateToRegister = {
                    navigationController.navigate(RegistrationScreenDestination)
                },
                    navigateToHome = {
                        navigationController.navigate(HomeScreenDestination){
                            popUpTo(LoginScreenDestination) { inclusive = true }
                        }
                    })
            }

            composable<RegistrationScreenDestination> {
                RegistrationScreen(navigateToLogin = {
                    navigationController.navigate(LoginScreenDestination)
                })
            }


            composable<AddTripScreenDestination> { TripScreen { navigationController.popBackStack() } }
            composable<AddGuideScreenDestination> { }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
                .background(Color.Transparent)// to stay above BottomAppBar
        ) {
            if (showButtons) {
                // Semi-transparent background
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x005C5C5C).copy(alpha = 0.5f))
                        .clickable {
                            rotated = false
                            showButtons = false
                        }
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            ) {
                AnimatedVisibility(
                    visible = showButtons,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    FloatingActionButton(
                        modifier = Modifier
                            .width(100.dp)
                            .height(70.dp),
                        shape = RoundedCornerShape(50.dp),
                        onClick = {
                            navigationController.navigate(AddTripScreenDestination)
                            rotated = false
                            showButtons = false
                        },
                        containerColor = White
                    ) {
                        Text(text = "Trip plan", textAlign = TextAlign.Center)
                    }
                }


                AnimatedVisibility(
                    visible = showButtons,
                    enter = scaleIn(animationSpec = tween(delayMillis = 50)),
                    exit = scaleOut()
                ) {
                    Row {
                        Spacer(Modifier.width(10.dp))
                        FloatingActionButton(
                            modifier = Modifier
                                .width(100.dp)
                                .height(70.dp),
                            shape = RoundedCornerShape(50.dp),
                            onClick = {
                                navigationController.navigate(AddGuideScreenDestination)
                                rotated = false
                                showButtons = false
                            },
                            containerColor = White
                        ) {
                            Text(text = "Guide", textAlign = TextAlign.Center)
                        }
                    }
                }

            }
        }
    }
}

