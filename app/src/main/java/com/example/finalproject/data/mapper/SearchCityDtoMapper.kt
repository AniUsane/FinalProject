package com.example.finalproject.data.mapper

import com.example.finalproject.data.model.DataObjectDto
import com.example.finalproject.domain.model.City

fun DataObjectDto.toDomain(): City {
    return City(
        name = this.name,
        countryName = this.address.countryName,
        iataCode = this.iataCode
    )
}