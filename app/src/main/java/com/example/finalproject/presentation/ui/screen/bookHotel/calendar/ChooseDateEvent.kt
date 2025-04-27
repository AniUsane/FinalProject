package com.example.finalproject.presentation.ui.screen.bookHotel.calendar

import java.time.LocalDate

sealed class ChooseDateEvent {
    data class StartDateSelected(val date: LocalDate) : ChooseDateEvent()
    data class EndDateSelected(val date: LocalDate) : ChooseDateEvent()
    data object DoneClicked : ChooseDateEvent()
    data object CancelClicked : ChooseDateEvent()
}