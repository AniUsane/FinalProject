package com.example.finalproject.domain.model

data class User(
    val id: String,
    val email: String,
    val password: String,
    val fullName: String? = null
)
