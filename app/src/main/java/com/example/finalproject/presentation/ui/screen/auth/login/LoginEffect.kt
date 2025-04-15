package com.example.finalproject.presentation.ui.screen.auth.login

sealed class LoginEffect {
    data object NavigateToHome: LoginEffect()
    data object NavigateToRegister: LoginEffect()
}