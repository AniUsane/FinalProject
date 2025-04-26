package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: String) = repository.deleteUser(userId)
}