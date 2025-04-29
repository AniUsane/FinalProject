package com.example.finalproject.presentation.ui.screen.addGuide.searchCity

import com.example.finalproject.domain.model.bookHotel.City

sealed class SearchCityEvent {
    data class OnQueryChanged(val query: String) : SearchCityEvent()
    data class OnCitySelected(val city: City) : SearchCityEvent()
//    data object OnCreateGuideClicked : SearchCityEvent()
}