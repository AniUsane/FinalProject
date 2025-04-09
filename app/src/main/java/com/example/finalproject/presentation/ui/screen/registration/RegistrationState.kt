package com.example.finalproject.presentation.ui.screen.registration

data class RegistrationState(
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val errorMessage: String? = null,
    val showEmailFields: Boolean = false,
    val fullName: String = ""
)