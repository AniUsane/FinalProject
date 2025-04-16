package com.example.finalproject.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    val id: String,
    val userId: String,
    val username: String,
    val fullName: String,
    val profileImageUrl: String? = null,
    val bio: String? = null,
    val trips: List<String> = emptyList(),
    val guides: List<String> = emptyList()
)
