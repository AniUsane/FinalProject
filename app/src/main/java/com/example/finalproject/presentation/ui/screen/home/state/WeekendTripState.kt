package com.example.finalproject.presentation.ui.screen.home.state

import com.example.finalproject.presentation.model.guide.WeekendTripUi

sealed class WeekendTripState {
    data object IsLoading: WeekendTripState()
    data object Idle : WeekendTripState()
    data class Success(val weekendTrips: List<WeekendTripUi>) : WeekendTripState()
    data class Error(val message : String) : WeekendTripState()
}