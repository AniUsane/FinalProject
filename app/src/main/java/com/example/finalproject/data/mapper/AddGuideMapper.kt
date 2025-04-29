package com.example.finalproject.data.mapper

import com.example.finalproject.data.model.addGuide.GuideDataDto
import com.example.finalproject.data.model.addGuide.GuideDto
import com.example.finalproject.domain.model.addGuide.Guide
import com.example.finalproject.domain.model.addGuide.GuideData

fun GuideDto.toDomain(): Guide = Guide(
    id = id,
    userId = userId,
    location = location,
    data = data.toDomain(),
    createdAt = createdAt
)

fun GuideDataDto.toDomain(): GuideData = GuideData(
    title = title,
    description = description,
    imageUrl = imageUrl,
    userImages = userImages
)

fun Guide.toDto(): GuideDto = GuideDto(
    id = id,
    userId = userId,
    location = location,
    data = data.toDto(),
    createdAt = createdAt
)

fun GuideData.toDto(): GuideDataDto = GuideDataDto(
    title = title,
    description = description,
    imageUrl = imageUrl,
    userImages = userImages
)