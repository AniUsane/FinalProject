package com.example.finalproject.data.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    val id: String,
    val message: String
)
