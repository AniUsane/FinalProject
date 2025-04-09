package com.example.finalproject.domain.repository

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.User
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(user: User): Flow<Resource<User>>
}