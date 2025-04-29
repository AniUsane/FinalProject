package com.example.finalproject.domain.model.addGuide

data class GuideData(
    val title: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val userImages: List<String>? = null
)