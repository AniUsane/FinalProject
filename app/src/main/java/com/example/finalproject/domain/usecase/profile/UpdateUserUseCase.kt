package com.example.finalproject.domain.usecase.profile

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.User
import com.example.finalproject.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): Flow<Resource<User>> {
        return repository.updateUser(user)
    }
}