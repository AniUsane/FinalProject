package com.example.finalproject.data.mapper

import com.example.finalproject.data.model.guide.PopularDestinationDto
import com.example.finalproject.data.model.guide.UserGuideDto
import com.example.finalproject.data.model.guide.WeekendTripDto
import com.example.finalproject.domain.model.guide.PopularDestination
import com.example.finalproject.domain.model.guide.UserGuide
import com.example.finalproject.domain.model.guide.WeekendTrip

fun UserGuideDto.toDomain () = UserGuide(
    title = title,
    username = username,
    content = content,
    summary = summary,
    date = date,
    imageUrl = imageUrl,
    userImageUrl = userImageUrl,
    id = id
)

fun WeekendTripDto.toDomain() = WeekendTrip(
    title = title,
    image = image,
    id = id
)

fun PopularDestinationDto.toDomain() = PopularDestination(
    title = title,
    image = image,
    id = id
)