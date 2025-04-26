package com.example.finalproject.presentation.mapper

import com.example.finalproject.domain.model.Profile
import com.example.finalproject.domain.model.User
import com.example.finalproject.presentation.model.ProfileUi
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

fun Profile.toPresentation() = ProfileUi(
    id = id,
    userId = userId,
    username = username,
    fullName = fullName,
    profileImageUrl = profileImageUrl,
    bio = bio,
    trips = trips,
    guides = guides
)

fun ProfileUi.toDomain() = Profile(
    id = id,
    userId = userId,
    username = username,
    fullName = fullName,
    profileImageUrl = profileImageUrl,
    bio = bio,
    trips = trips,
    guides = guides
)