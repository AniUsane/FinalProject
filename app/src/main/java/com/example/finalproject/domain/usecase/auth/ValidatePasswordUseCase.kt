package com.example.finalproject.domain.usecase.auth

import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): Boolean {
        return password.isNotEmpty() && password.matches(passwordRegex)
    }

    private val passwordRegex = Regex(
        "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$"
    )
}