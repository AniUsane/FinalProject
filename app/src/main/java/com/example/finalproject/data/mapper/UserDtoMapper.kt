package com.example.finalproject.data.mapper

import com.example.finalproject.data.model.auth.LoginResponseDto
import com.example.finalproject.data.model.auth.RegisterResponseDto
import com.example.finalproject.data.model.auth.UserDto
import com.example.finalproject.domain.model.LoginResponse
import com.example.finalproject.domain.model.RegisterResponse
import com.example.finalproject.domain.model.User

fun UserDto.toDomain() = User(
    id = id,
    email = email,
    password = password,
    fullName = fullName ?: ""
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
    password = password,
    fullName = fullName,
)