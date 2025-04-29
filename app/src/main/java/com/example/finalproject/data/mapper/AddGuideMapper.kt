package com.example.finalproject.data.mapper

import com.example.finalproject.data.model.addGuide.GuideData
import com.example.finalproject.data.model.addGuide.GuideDataDto
import com.example.finalproject.data.model.addGuide.GuideDto
import com.example.finalproject.data.model.addGuide.Location
import com.example.finalproject.data.model.addGuide.LocationDto
import com.example.finalproject.domain.model.addGuide.Guide

fun GuideDto.toDomain(): Guide = Guide(
    id = id,
    userId = userId,
    location = location.toDomain(),
    data = data.toDomain(),
    createdAt = createdAt
)

fun LocationDto.toDomain(): Location = Location(
    id = id,
    name = name,
    type = type,
    country = country
)

fun GuideDataDto.toDomain(): GuideData = GuideData(
    title = title,
    description = description,
    imageUrl = imageUrl
)

fun Guide.toDto(): GuideDto = GuideDto(
    id = id,
    userId = userId,
    location = location.toDto(),
    data = data.toDto(),
    createdAt = createdAt
)

fun Location.toDto(): LocationDto = LocationDto(
    id = id,
    name = name,
    type = type,
    country = country
)

fun GuideData.toDto(): GuideDataDto = GuideDataDto(
    title = title,
    description = description,
    imageUrl = imageUrl
)