package com.example.finalproject.presentation.ui.screen.login

sealed class LoginEvent {
    data class OnEmailChanged(val email: String) : LoginEvent()
    data class OnPasswordChanged(val password: String) : LoginEvent()
    data object OnTogglePasswordVisibility : LoginEvent()
    data object OnToggleEmailVisibility: LoginEvent()
    data object OnSubmit : LoginEvent()
    data object NavigateToRegister : LoginEvent()
    data class OnRememberMeChecked(val checked: Boolean) : LoginEvent()
}