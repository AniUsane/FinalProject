package com.example.finalproject.domain.repository

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun deleteUser(userId: String): Flow<Resource<Unit>>
    suspend fun updateUser(user: User): Flow<Resource<User>>
    suspend fun getUserById(id: String): User?
}