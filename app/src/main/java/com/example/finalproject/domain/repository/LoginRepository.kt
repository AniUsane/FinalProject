package com.example.finalproject.domain.repository

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.LoginResponse
import com.example.finalproject.domain.model.User
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(email: String, password: String): Flow<Resource<User>>
    suspend fun loginResponse(): Flow<Resource<LoginResponse>>
}