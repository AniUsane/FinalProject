package com.example.finalproject.presentation.ui.screen

sealed class Screens (val screen: String) {
    data object Home: Screens("home")
    data object Booking: Screens("booking")
    data object Suggestion: Screens("suggestion")
    data object Profile: Screens("profile")
}