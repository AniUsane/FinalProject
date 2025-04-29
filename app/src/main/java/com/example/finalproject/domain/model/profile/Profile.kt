package com.example.finalproject.domain.model.profile

import com.example.finalproject.domain.model.addGuide.Guide

data class Profile(
    val id: String,
    val userId: String,
    val username: String,
    val fullName: String,
    val profileImageUrl: String? = null,
    val bio: String? = null,
    val trips: List<String> = emptyList(),
    val guide: List<Guide> = emptyList()
)
