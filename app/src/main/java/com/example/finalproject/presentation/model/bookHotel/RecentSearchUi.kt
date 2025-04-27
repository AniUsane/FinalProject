package com.example.finalproject.presentation.model.bookHotel

data class RecentSearchUi(
    val searchQuery: String,
    val checkInDate: String,
    val checkOutDate: String,
    val guests: Int,
    val rooms: Int
)
