package com.example.finalproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.presentation.ui.screen.trip.TripScreen
import kotlinx.serialization.Serializable

@Serializable
data object TripScreenDestination

@Composable
fun TripNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = TripScreenDestination) {
        composable<TripScreenDestination> {
            TripScreen (onNavigateBack = {navController.popBackStack()})
        }
    }
}