package com.example.finalproject.domain.model.addGuide


data class Guide(
    val id: String,
    val userId: String,
    val location: String,
    val data: GuideData,
    val createdAt: String
)