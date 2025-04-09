package com.example.finalproject.presentation.ui.screen.registration

sealed class RegistrationEvent {
    data class OnEmailChanged(val email: String): RegistrationEvent()
    data class OnPasswordChanged(val password: String): RegistrationEvent()
    data object OnTogglePasswordVisibility: RegistrationEvent()
    data object OnSubmit: RegistrationEvent()
    data object ToggleEmailFieldsVisibility: RegistrationEvent()
    data class OnFullNameChanged(val fullName: String): RegistrationEvent()
    data object NavigateToLogin: RegistrationEvent()
}