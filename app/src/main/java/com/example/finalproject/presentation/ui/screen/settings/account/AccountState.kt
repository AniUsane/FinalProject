package com.example.finalproject.presentation.ui.screen.settings.account

import com.example.finalproject.domain.model.Profile
import com.example.finalproject.domain.model.User

data class AccountState(
    val id: String = "",
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val fullName: String = "",
    val profileImageUrl: String = "",
    val emailError: String? = null,
    val isLoading: Boolean = false,
    val user: User? = null,
    val profile: Profile? = null
    ){
    val nameInitial: String
        get() = email.firstOrNull()?.uppercaseChar()?.toString() ?: ""
}
