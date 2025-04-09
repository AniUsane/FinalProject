package com.example.finalproject.presentation.ui.screen.registration

sealed class RegistrationEffect {
    data object NavigateToLogin: RegistrationEffect()
    data class ShowSnackBar(val message: String): RegistrationEffect()
}