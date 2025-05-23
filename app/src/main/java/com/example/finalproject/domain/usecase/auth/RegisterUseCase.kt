package com.example.finalproject.domain.usecase.auth

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.profile.User
import com.example.finalproject.domain.repository.auth.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke(user: User): Flow<Resource<User>> {
        return registerRepository.register(user)
    }
}