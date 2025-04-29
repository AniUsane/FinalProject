package com.example.finalproject.presentation.mapper

import com.example.finalproject.data.model.addGuide.GuideData
import com.example.finalproject.data.model.addGuide.Location
import com.example.finalproject.domain.model.addGuide.Guide
import com.example.finalproject.domain.model.bookHotel.City
import com.example.finalproject.presentation.model.addGuide.GuideDataUi
import com.example.finalproject.presentation.model.addGuide.GuideUi
import com.example.finalproject.presentation.model.addGuide.LocationUi

fun Guide.toUi(): GuideUi = GuideUi(
    id = id,
    userId = userId,
    location = location.toUi(),
    data = data.toUi(),
    createdAt = createdAt
)

fun Location.toUi(): LocationUi = LocationUi(
    id = id,
    name = name,
    type = type,
    country = country
)

fun GuideData.toUi(): GuideDataUi = GuideDataUi(
    title = title,
    description = description,
    imageUrl = imageUrl
)

fun GuideUi.toDomain(): Guide = Guide(
    id = id,
    userId = userId,
    location = location.toDomain(),
    data = data.toDomain(),
    createdAt = createdAt
)

fun LocationUi.toDomain(): Location = Location(
    id = id,
    name = name,
    type = type,
    country = country
)

fun GuideDataUi.toDomain(): GuideData = GuideData(
    title = title,
    description = description,
    imageUrl = imageUrl
)


fun City.toLocationUi(): LocationUi {
    return LocationUi(
        id = this.iataCode,
        name = this.name,
        type = this.type,
        country = this.countryName
    )
}