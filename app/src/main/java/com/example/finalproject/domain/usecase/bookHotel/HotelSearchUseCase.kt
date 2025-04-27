package com.example.finalproject.domain.usecase.bookHotel

import com.example.finalproject.presentation.model.bookHotel.Travelers
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HotelSearchUseCase @Inject constructor() {
    operator fun invoke(
        destination: String,
        checkIn: LocalDate,
        checkOut: LocalDate,
        travelers: Travelers
    ): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val checkInFormatted = checkIn.format(formatter)
        val checkOutFormatted = checkOut.format(formatter)

        return buildString {
            append("https://www.booking.com/searchresults.html?")
            append("ss=${destination}")
            append("&checkin=${checkInFormatted}")
            append("&checkout=${checkOutFormatted}")
            append("&group_adults=${travelers.adults}")
            append("&group_children=${travelers.children}")
            append("&no_rooms=${travelers.rooms}")
        }
    }
}