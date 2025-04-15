package com.example.finalproject.presentation.ui.screen.auth.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val showEmailFields: Boolean = false,
    val rememberMe: Boolean = false
)
