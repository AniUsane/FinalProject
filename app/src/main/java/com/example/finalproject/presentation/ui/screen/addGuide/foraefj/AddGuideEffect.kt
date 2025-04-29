package com.example.finalproject.presentation.ui.screen.addGuide.foraefj

sealed class AddGuideEffect {
    data object GuideCreatedSuccessfully : AddGuideEffect()
    data object NavigateToSearchCityScreen: AddGuideEffect()
    data class ShowSnackBar(val message: String) : AddGuideEffect()
}