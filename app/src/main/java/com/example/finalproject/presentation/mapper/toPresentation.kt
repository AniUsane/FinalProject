package com.example.finalproject.presentation.mapper

import com.example.finalproject.domain.model.guide.PopularDestination
import com.example.finalproject.domain.model.guide.UserGuide
import com.example.finalproject.domain.model.guide.WeekendTrip
import com.example.finalproject.presentation.model.guide.PopularDestinationUi
import com.example.finalproject.presentation.model.guide.UserGuideUi
import com.example.finalproject.presentation.model.guide.WeekendTripUi

fun UserGuide.toPresentation() = UserGuideUi(
    title = title,
    username = username,
    content = content,
    summary = summary,
    date = date,
    imageUrl = imageUrl,
    userImageUrl = userImageUrl,
    id = id
)

fun WeekendTrip.toPresentation() = WeekendTripUi(
    title = title,
    image = image,
    id = id
)

fun PopularDestination.toPresentation() = PopularDestinationUi(
    title = title,
    image = image,
    id = id
)