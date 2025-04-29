package com.example.finalproject.presentation.ui.screen.addGuide.foraefj

import com.example.finalproject.domain.model.bookHotel.City

data class AddGuideState(
    val query: String = "",
    val selectedCity: City? = null,
    val isCreatingGuide: Boolean = false
)
