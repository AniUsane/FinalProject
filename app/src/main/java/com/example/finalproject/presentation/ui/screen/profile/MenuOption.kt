package com.example.finalproject.presentation.ui.screen.profile

import com.example.finalproject.R

sealed class MenuOption(val labelResourceId: Int) {
    data object HelpHowTo : MenuOption(R.string.help_how_to)
    data object FeedbackSupport : MenuOption(R.string.feedback_support)
    data object Settings : MenuOption(R.string.settings)
}
