package com.example.finalproject.data.repository.profile

import com.example.finalproject.common.HandleResponse
import com.example.finalproject.common.Resource
import com.example.finalproject.data.mapper.asResource
import com.example.finalproject.data.mapper.toDomain
import com.example.finalproject.data.mapper.toDto
import com.example.finalproject.data.service.UserService
import com.example.finalproject.domain.model.profile.User
import com.example.finalproject.domain.repository.profile.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val service: UserService,
    private val handleResponse: HandleResponse
): UserRepository {
    override suspend fun deleteUser(userId: String): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            service.deleteUser(userId)
        }
    }

    override suspend fun updateUser(user: User): Flow<Resource<User>> {
        return handleResponse.safeApiCall {
            service.updateUser(user.id, user.toDto())
        }.asResource { it.toDomain() }
    }

    override suspend fun getUserById(id: String): User? {
        return service.getUserById(id).body()?.toDomain()
    }

}