package com.example.finalproject.presentation.ui.screen.auth.registration

sealed class RegistrationEffect {
    data object NavigateToLogin: RegistrationEffect()
    data class ShowSnackBar(val message: String): RegistrationEffect()
}