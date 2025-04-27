package com.example.finalproject.domain.model.bookHotel

data class RecentSearch(
    val searchQuery: String,
    val checkInDate: String,
    val checkOutDate: String,
    val guests: Int,
    val rooms: Int,
    val userId: String
)
