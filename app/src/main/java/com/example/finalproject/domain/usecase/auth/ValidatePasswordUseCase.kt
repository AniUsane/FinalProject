package com.example.finalproject.domain.usecase.auth

class ValidatePasswordUseCase {
    operator fun invoke(password: String): Boolean {
        return password.isNotEmpty() && password.matches(passwordRegex)
    }

    private val passwordRegex = Regex(
        "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$"
    )
}