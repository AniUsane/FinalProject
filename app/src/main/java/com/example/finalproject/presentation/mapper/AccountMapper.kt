package com.example.finalproject.presentation.mapper

import com.example.finalproject.domain.model.profile.Profile
import com.example.finalproject.domain.model.profile.User
import com.example.finalproject.presentation.ui.screen.settings.account.AccountState

fun Profile.toAccountState(user: User): AccountState {
    return AccountState(
        id = id,
        name = user.fullName ?: "",
        username = username,
        email = user.email,
        profileImageUrl = profileImageUrl ?: ""
    )
}