package com.example.finalproject.presentation.ui.screen.profile

sealed class ProfileEvent {
    data object LoadProfile: ProfileEvent()
    data object LogoutClicked: ProfileEvent()
    data object ChangeImageClicked: ProfileEvent()
    data class OnProfileImageSelected(val uri: String): ProfileEvent()
    data object DeleteAccount: ProfileEvent()
    data object SettingsClicked : ProfileEvent()
    data object HelpClicked : ProfileEvent()
    data object FeedbackClicked : ProfileEvent()
    data object AddTrip: ProfileEvent()
    data object AddGuide: ProfileEvent()
}