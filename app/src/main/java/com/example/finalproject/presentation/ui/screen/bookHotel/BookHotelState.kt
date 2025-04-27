package com.example.finalproject.presentation.ui.screen.bookHotel

import com.example.finalproject.presentation.model.bookHotel.RecentSearchUi
import com.example.finalproject.presentation.model.bookHotel.Travelers
import java.time.LocalDate

data class BookHotelState(
    val destination: String = "",
    val checkIn: LocalDate = LocalDate.now(),
    val checkOut: LocalDate = LocalDate.now().plusDays(1),
    val travelers: Travelers = Travelers(1,2,0),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val recentSearches: List<RecentSearchUi> = emptyList(),
)