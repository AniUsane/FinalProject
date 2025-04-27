package com.example.finalproject.data.mapper

import com.example.finalproject.data.model.bookHotel.RecentSearchDto
import com.example.finalproject.domain.model.bookHotel.RecentSearch

fun RecentSearchDto.toDomain(): RecentSearch {
    return RecentSearch(
        searchQuery = searchQuery,
        checkInDate = checkInDate,
        checkOutDate = checkOutDate,
        guests = guests,
        rooms = rooms,
        userId = userId
    )
}