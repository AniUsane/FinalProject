package com.example.finalproject.presentation.model.addGuide


data class GuideUi(
    val id: String,
    val userId: String,
    val location: String,
    val title: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val userImages: List<String>? = null,
    val createdAt: String
)