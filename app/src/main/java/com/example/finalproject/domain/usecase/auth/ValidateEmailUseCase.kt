package com.example.finalproject.domain.usecase.auth

import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {
    operator fun invoke(email: String): Boolean {
        return email.isNotEmpty() && email.matches(emailRegex)
    }

    private val emailRegex = Regex(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    )

}