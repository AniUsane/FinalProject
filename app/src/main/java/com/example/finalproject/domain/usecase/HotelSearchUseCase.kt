package com.example.finalproject.domain.usecase

import com.example.finalproject.presentation.ui.screen.bookHotel.Travelers
import java.time.LocalDate

class HotelSearchUseCase {
    operator fun invoke(
        destination: String,
        checkIn: LocalDate,
        checkOut: LocalDate,
        travelers: Travelers
    ):String {
        return "https://www.booking.com/searchresults.html?" +
                "ss=${destination}&" +
                "checkin_year_month_monthday=$checkIn&" +
                "checkout_year_month_monthday=$checkOut&" +
                "group_adults=${travelers.adults}&" +
                "group_children=${travelers.children}&" +
                "no_rooms=${travelers.rooms}"
    }
}