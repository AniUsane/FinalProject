package com.example.finalproject.presentation.ui.screen.bookHotel.citySearch

import com.example.finalproject.domain.model.bookHotel.City

sealed class CitySearchEffect {
    data class ShowSnackBar(val message: String) : CitySearchEffect()
    data class NavigateBackWithResult(val city: City) : CitySearchEffect()
}