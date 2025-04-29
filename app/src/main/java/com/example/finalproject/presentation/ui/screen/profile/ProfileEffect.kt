package com.example.finalproject.presentation.ui.screen.profile

sealed class ProfileEffect {
    data object NavigateToLogin: ProfileEffect()
    data class ShowSnackBar(val message: String): ProfileEffect()
    data object OpenImagePicker: ProfileEffect()
    data object ShowSettingsDialog : ProfileEffect()
    data object ShowHelpDialog : ProfileEffect()
    data object ShowFeedbackDialog : ProfileEffect()
    data object NavigateToAddGuide: ProfileEffect()
}