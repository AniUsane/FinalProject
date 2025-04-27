package com.example.finalproject.presentation.ui.screen.bookHotel.citySearch
import com.example.finalproject.domain.model.bookHotel.City

sealed class CitySearchEvent {
    data class OnQueryChanged(val query: String) : CitySearchEvent()
    data class OnCitySelected(val city: City) : CitySearchEvent()
}