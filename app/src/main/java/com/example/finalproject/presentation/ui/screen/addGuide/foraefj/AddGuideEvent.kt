package com.example.finalproject.presentation.ui.screen.addGuide.foraefj

import com.example.finalproject.domain.model.bookHotel.City

sealed class AddGuideEvent {
    data object OnSearchCityFieldClicked: AddGuideEvent()
    data class OnQueryChanged(val query: String) : AddGuideEvent()
    data class OnCitySelected(val city: City) : AddGuideEvent()
    data object OnCreateGuideClicked : AddGuideEvent()
}