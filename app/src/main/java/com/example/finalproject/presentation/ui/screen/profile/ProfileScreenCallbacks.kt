package com.example.finalproject.presentation.ui.screen.profile

data class ProfileScreenCallbacks(
    val navigateToLogin: () -> Unit,
    val showSettingsDialog: () -> Unit = {},
    val showHelpDialog: () -> Unit = {},
    val showFeedbackDialog: () -> Unit = {},
    val showSnackBar: (String) -> Unit = {},
    val navigateToAddGuide: () -> Unit = {}
)