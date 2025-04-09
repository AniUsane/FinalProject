package com.example.finalproject.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val email: String,
    val password: String,
    val fullName: String? = null
)
