package com.example.finalproject.presentation.ui.screen.bookHotel

import java.time.LocalDate

sealed class BookHotelEvent {
    data class OnDestinationChanged(val destination: String): BookHotelEvent()
    data class OnDateChanged(val checkIn: LocalDate, val checkOut: LocalDate): BookHotelEvent()
    data class OnTravelersChanged(val travelers: Travelers): BookHotelEvent()
    data object OnSearchClicked: BookHotelEvent()
}
