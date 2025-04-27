package com.example.finalproject.domain.repository.auth

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.auth.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(email: String, password: String): Flow<Resource<LoginResponse>>
}