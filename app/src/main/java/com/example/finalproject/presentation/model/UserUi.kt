package com.example.finalproject.presentation.model

data class UserUi(
    val id: String,
    val email: String,
    val password: String,
    val fullName: String? = null
)
