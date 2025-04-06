package com.example.finalproject.data.repository

import com.example.finalproject.common.HandleResponse
import com.example.finalproject.common.Resource
import com.example.finalproject.data.mapper.asResource
import com.example.finalproject.data.mapper.toDomain
import com.example.finalproject.data.service.AuthService
import com.example.finalproject.domain.model.LoginResponse
import com.example.finalproject.domain.model.User
import com.example.finalproject.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val service: AuthService,
    private val handleResponse: HandleResponse
): LoginRepository {
    override suspend fun login(email: String, password: String): Flow<Resource<User>> {
        return handleResponse.safeApiCall {
            val users = service.login(email, password).body()
            val user = users?.firstOrNull { it.password == password }

            if (user != null) {
                retrofit2.Response.success(user)
            } else {
                retrofit2.Response.error(
                    401, "Invalid email or password".toResponseBody("text/plain".toMediaType())
                )
            }
        }.asResource { it.toDomain() }
    }

    override suspend fun loginResponse(): Flow<Resource<LoginResponse>> {
        return handleResponse.safeApiCall {
            val response = service.getLoginResponse().body()
            val successful = response?.firstOrNull { it.status == "success" }

            if (successful != null) {
                retrofit2.Response.success(successful)
            } else {
                retrofit2.Response.error(
                    401, "Invalid login credentials".toResponseBody("text/plain".toMediaType())
                )
            }
        }.asResource { it.toDomain() }
    }
}