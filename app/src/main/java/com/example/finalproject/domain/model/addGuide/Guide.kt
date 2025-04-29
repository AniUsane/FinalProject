package com.example.finalproject.domain.model.addGuide

import com.example.finalproject.data.model.addGuide.GuideData
import com.example.finalproject.data.model.addGuide.Location


data class Guide(
    val id: String,
    val userId: String,
    val location: Location,
    val data: GuideData,
    val createdAt: String
)