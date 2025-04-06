package com.example.finalproject.data.repository

import com.example.finalproject.common.HandleResponse
import com.example.finalproject.common.Resource
import com.example.finalproject.data.mapper.asResource
import com.example.finalproject.data.mapper.toDomain
import com.example.finalproject.data.mapper.toDto
import com.example.finalproject.data.service.AuthService
import com.example.finalproject.domain.model.RegisterResponse
import com.example.finalproject.domain.model.User
import com.example.finalproject.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val service: AuthService,
    private val handleResponse: HandleResponse
): RegisterRepository {
    override suspend fun register(user: User): Flow<Resource<User>> {
        return handleResponse.safeApiCall {
            service.register(user.toDto())
        }.asResource {
            it.toDomain()
        }
    }

    override suspend fun registrationResponse(): Flow<Resource<RegisterResponse>> {
        return handleResponse.safeApiCall {
            val response = service.getRegisterResponse().body()
            val successful = response?.firstOrNull { it.message == "Registration successful" }

            if (successful != null) {
                retrofit2.Response.success(successful)
            } else {
                retrofit2.Response.error(
                    400,
                    "Registration failed".toResponseBody("text/plain".toMediaType())
                )
            }
        }.asResource {
            it.toDomain()
        }
    }
}