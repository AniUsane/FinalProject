package com.example.finalproject.domain.usecase.auth

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.LoginResponse
import com.example.finalproject.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<LoginResponse>> {
        return loginRepository.login(email, password)
    }
}