package com.example.finalproject.presentation.ui.screen.settings.account

sealed class AccountEvent {
    data class NameChanged(val name: String) : AccountEvent()
    data class UsernameChanged(val username: String) : AccountEvent()
    data class EmailChanged(val email: String) : AccountEvent()
    data object SaveClicked : AccountEvent()
    data object DeleteClicked : AccountEvent()
    data object LoadAccount: AccountEvent()
}