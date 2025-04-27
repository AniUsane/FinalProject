package com.example.finalproject.data.model.bookHotel

import kotlinx.serialization.Serializable

@Serializable
data class RecentSearchDto(
    val id: String,
    val searchQuery: String,
    val userId: String,
    val checkInDate: String,
    val checkOutDate: String,
    val guests: Int,
    val rooms: Int
)
