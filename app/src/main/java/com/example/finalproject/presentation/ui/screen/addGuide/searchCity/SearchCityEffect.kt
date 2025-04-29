package com.example.finalproject.presentation.ui.screen.addGuide.searchCity

sealed class SearchCityEffect {
    data object GuideCreatedSuccessfully : SearchCityEffect()
    data class ShowSnackBar(val message: String) : SearchCityEffect()
}