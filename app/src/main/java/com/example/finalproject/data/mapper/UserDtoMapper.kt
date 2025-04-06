package com.example.finalproject.data.mapper

import com.example.finalproject.data.model.LoginResponseDto
import com.example.finalproject.data.model.RegisterResponseDto
import com.example.finalproject.data.model.UserDto
import com.example.finalproject.domain.model.LoginResponse
import com.example.finalproject.domain.model.RegisterResponse
import com.example.finalproject.domain.model.User

fun UserDto.toDomain() = User(
    id = id,
    email = email,
    fullName = fullName,
    photoUrl = photoUrl ?: ""
)

fun LoginResponseDto.toDomain() = LoginResponse(
    id = id,
    status = status,
    message = message,
    token = token,
    userId = userId
)

fun RegisterResponseDto.toDomain() = RegisterResponse(
    id = id,
    message = message
)

fun User.toDto() = UserDto(
    id = id,
    email = email,
    password = "",
    fullName = fullName,
    photoUrl = photoUrl
)