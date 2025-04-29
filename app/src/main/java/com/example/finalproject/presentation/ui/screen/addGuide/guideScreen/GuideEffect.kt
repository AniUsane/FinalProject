package com.example.finalproject.presentation.ui.screen.addGuide.guideScreen

sealed class GuideEffect {
    data class ShowSnackBar(val message: String) : GuideEffect()
}