package com.example.finalproject.domain.model.guide


data class UserGuide(
    val title: String,
    val username: String,
    val content: String,
    val summary: String,
    val date: String,
    val imageUrl: String,
    val userImageUrl: String,
    val id: Int
)
