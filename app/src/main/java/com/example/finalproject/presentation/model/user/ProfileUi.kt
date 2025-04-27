package com.example.finalproject.presentation.model.user

data class ProfileUi(
    val id: String,
    val userId: String,
    val username: String,
    val fullName: String,
    val profileImageUrl: String? = null,
    val bio: String? = null,
    val trips: List<String> = emptyList(),
    val guides: List<String> = emptyList()
)
