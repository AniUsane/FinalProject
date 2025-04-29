package com.example.finalproject.presentation.model.user

import com.example.finalproject.presentation.model.addGuide.GuideUi

data class ProfileUi(
    val id: String,
    val userId: String,
    val username: String,
    val fullName: String,
    val profileImageUrl: String? = null,
    val bio: String? = null,
    val trips: List<String> = emptyList(),
    val guide: List<GuideUi> = emptyList()
)
