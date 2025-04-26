package com.example.finalproject.presentation.ui.screen.profile

import com.example.finalproject.presentation.model.ProfileUi

data class ProfileState(
    val isLoading: Boolean = false,
    val profile: ProfileUi? = null,
    val errorMessage: String? = null
)
