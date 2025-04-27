package com.example.finalproject.presentation.ui.screen.bookHotel

import com.example.finalproject.presentation.model.bookHotel.Travelers
import java.time.LocalDate

sealed class BookHotelEvent {
    data object OnDestinationFieldClicked: BookHotelEvent()
    data class OnDestinationChanged(val destination: String): BookHotelEvent()
    data class OnDateChanged(val checkIn: LocalDate, val checkOut: LocalDate): BookHotelEvent()
    data class OnTravelersChanged(val travelers: Travelers): BookHotelEvent()
    data object OnSearchClicked: BookHotelEvent()
    data object OnDateFieldClicked : BookHotelEvent()
    data object OnTravelersFieldClicked : BookHotelEvent()
}