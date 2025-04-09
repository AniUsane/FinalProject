package com.example.finalproject.domain.model

data class LoginResponse(
    val id: String,
    val status: String,
    val message: String,
    val token: String? = null,
    val userId: String? = null
)