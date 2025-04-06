package com.example.finalproject.domain.model

data class User(
    val id: String,
    val email: String,
    val fullName: String,
    val photoUrl: String? = null
)
