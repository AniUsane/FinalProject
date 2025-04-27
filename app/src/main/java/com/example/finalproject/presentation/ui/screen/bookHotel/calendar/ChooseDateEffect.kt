package com.example.finalproject.presentation.ui.screen.bookHotel.calendar

import java.time.LocalDate

sealed class ChooseDateEffect {
    data class Done(val dates: Pair<LocalDate, LocalDate>) : ChooseDateEffect()
    data object Cancel : ChooseDateEffect()
}