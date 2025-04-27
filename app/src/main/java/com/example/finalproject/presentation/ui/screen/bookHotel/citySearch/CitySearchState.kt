package com.example.finalproject.presentation.ui.screen.bookHotel.citySearch

import com.example.finalproject.domain.model.bookHotel.City

data class CitySearchState(
    val query: String = "",
    val cityList: List<City> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)