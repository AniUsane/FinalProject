package com.example.finalproject.data.model.addGuide

import kotlinx.serialization.Serializable

@Serializable
data class GuideDto(
    val id: String,
    val userId: String,
    val location: String,
    val data: GuideDataDto,
    val createdAt: String
)