package com.example.finalproject.presentation.ui.screen.addGuide.searchCity

import com.example.finalproject.domain.model.bookHotel.City

data class SearchCityState(
    val query: String = "",
    val isLoading: Boolean = false,
    val cityList: List<City> = emptyList(),
    val selectedCity: City? = null,
    val isGuideCreating: Boolean = false,
    val errorMessage: String? = null
)