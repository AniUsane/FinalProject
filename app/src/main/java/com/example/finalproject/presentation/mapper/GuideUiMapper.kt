package com.example.finalproject.presentation.mapper

import com.example.finalproject.domain.model.addGuide.Guide
import com.example.finalproject.domain.model.addGuide.GuideData
import com.example.finalproject.presentation.model.addGuide.GuideDataUi
import com.example.finalproject.presentation.model.addGuide.GuideUi

fun Guide.toPresentation(): GuideUi = GuideUi(
    id = id,
    userId = userId,
    location = location,
    data = data.toPresentation(),
    createdAt = createdAt
)


fun GuideData.toPresentation(): GuideDataUi = GuideDataUi(
    title = title,
    description = description,
    imageUrl = imageUrl,
    userImages = userImages
)

fun GuideUi.toDomain(): Guide = Guide(
    id = id,
    userId = userId,
    location = location,
    data = data.toDomain(),
    createdAt = createdAt
)


fun GuideDataUi.toDomain(): GuideData = GuideData(
    title = title,
    description = description,
    imageUrl = imageUrl,
    userImages = userImages
)
