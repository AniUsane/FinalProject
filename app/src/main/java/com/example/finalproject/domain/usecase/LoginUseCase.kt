package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        loginRepository.login(email = email, password = password)
}