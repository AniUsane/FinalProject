package com.example.finalproject.presentation.ui.screen.bookHotel.citySearch

import com.example.finalproject.domain.model.City

sealed class CitySearchEffect {
    data class ShowSnackbar(val message: String) : CitySearchEffect()
    data class NavigateBackWithResult(val city: City) : CitySearchEffect()
}