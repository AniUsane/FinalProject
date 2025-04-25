package com.example.finalproject.presentation.ui.screen.settings.account

sealed class AccountEffect{
    data class ShowSnackBar(val message: String) : AccountEffect()
    data object NavigateToLogin : AccountEffect()
}
