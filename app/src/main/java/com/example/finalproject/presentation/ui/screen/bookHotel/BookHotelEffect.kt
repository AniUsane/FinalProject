package com.example.finalproject.presentation.ui.screen.bookHotel

sealed class BookHotelEffect {
    data class ShowSnackBar(val message: String): BookHotelEffect()
    data class NavigateToSearchResults(val url: String): BookHotelEffect()
    data object OpenDatePicker : BookHotelEffect()
    data object OpenTravelerBottomSheet : BookHotelEffect()
    data object OpenCitySearchScreen: BookHotelEffect()
}