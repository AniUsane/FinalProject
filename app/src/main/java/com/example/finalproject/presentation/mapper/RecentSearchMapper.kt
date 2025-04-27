package com.example.finalproject.presentation.mapper

import com.example.finalproject.domain.model.bookHotel.RecentSearch
import com.example.finalproject.presentation.model.bookHotel.RecentSearchUi

fun RecentSearchUi.toDomain(userId: String): RecentSearch {
    return RecentSearch(
        searchQuery = searchQuery,
        checkInDate = checkInDate,
        checkOutDate = checkOutDate,
        guests = guests,
        rooms = rooms,
        userId = userId
    )
}