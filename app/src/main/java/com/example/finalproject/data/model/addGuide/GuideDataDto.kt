package com.example.finalproject.data.model.addGuide

import kotlinx.serialization.Serializable

@Serializable
data class GuideDataDto(
    val title: String? = null,
    val description: String? = null,
    val imageUrl: String? = null
)