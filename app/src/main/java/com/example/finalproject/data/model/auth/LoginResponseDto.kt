package com.example.finalproject.data.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val id: String,
    val status: String,
    val message: String,
    val token: String? = null,
    val userId: String? = null
)
