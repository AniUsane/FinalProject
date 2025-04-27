package com.example.finalproject.data.mapper

import com.example.finalproject.data.model.bookHotel.DataObjectDto
import com.example.finalproject.domain.model.bookHotel.City

fun DataObjectDto.toDomain(): City {
    return City(
        name = name,
        countryName = address?.countryName ?: "",
        iataCode = iataCode.orEmpty(),
        type = subType
    )
}