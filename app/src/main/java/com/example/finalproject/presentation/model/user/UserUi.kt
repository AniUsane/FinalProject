package com.example.finalproject.presentation.model.user

data class UserUi(
    val id: String,
    val email: String,
    val password: String,
    val fullName: String? = null
)
