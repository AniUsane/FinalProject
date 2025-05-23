package com.example.finalproject.data.mapper

import com.example.finalproject.data.model.profile.ProfileDto
import com.example.finalproject.domain.model.profile.Profile

fun ProfileDto.toDomain(): Profile =
    Profile(
        id = id,
        userId = userId,
        username = username,
        fullName = fullName,
        profileImageUrl = profileImageUrl,
        bio = bio,
        trips = trips,
        guides = guides
    )

fun Profile.toDto(): ProfileDto =
    ProfileDto(
        id = id,
        userId = userId,
        username = username,
        fullName = fullName,
        profileImageUrl = profileImageUrl,
        bio = bio,
        trips = trips,
        guides = guides
    )
