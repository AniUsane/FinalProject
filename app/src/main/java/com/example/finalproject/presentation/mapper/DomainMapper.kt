package com.example.finalproject.presentation.mapper

import com.example.finalproject.domain.model.User
import com.example.finalproject.presentation.model.UserUi

fun User.toPresentation() = UserUi(
    id = id,
    email = email,
    password = password,
    fullName = fullName
)

fun UserUi.toDomain() = User(
    id = "",
    email = email,
    password = password,
    fullName = fullName,
)