package com.example.finalproject.presentation.ui.screen.login

sealed class LoginEffect {
    data object NavigateToHome: LoginEffect()
    data object NavigateToRegister: LoginEffect()
}