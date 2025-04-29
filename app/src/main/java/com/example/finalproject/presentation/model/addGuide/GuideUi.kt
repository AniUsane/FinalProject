package com.example.finalproject.presentation.model.addGuide


data class GuideUi(
    val id: String,
    val userId: String,
    val location: String,
    val data: GuideDataUi,
    val createdAt: String
)