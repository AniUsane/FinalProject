package com.example.finalproject.data.model.unsplash

import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImageDto(
    val id: String,
    val urls: UnsplashUrlDto
)