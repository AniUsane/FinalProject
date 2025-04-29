package com.example.finalproject.presentation.ui.screen.addGuide.foraefj

sealed class AddGuideEffect {
    data object NavigateToProfile : AddGuideEffect()
    data class ShowSnackBar(val message: String) : AddGuideEffect()
    data class NavigateToGuide(val guideId: String) : AddGuideEffect()
}