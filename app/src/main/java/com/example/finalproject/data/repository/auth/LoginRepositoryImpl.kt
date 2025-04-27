package com.example.finalproject.data.repository.auth

import com.example.finalproject.common.HandleResponse
import com.example.finalproject.common.Resource
import com.example.finalproject.data.mapper.asResource
import com.example.finalproject.data.mapper.toDomain
import com.example.finalproject.data.model.auth.LoginResponseDto
import com.example.finalproject.data.service.AuthService
import com.example.finalproject.domain.model.auth.LoginResponse
import com.example.finalproject.domain.repository.auth.LoginRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val service: AuthService,
    private val handleResponse: HandleResponse
): LoginRepository {
    override suspend fun login(email: String, password: String): Flow<Resource<LoginResponse>> {
        return handleResponse.safeApiCall {
            val users = service.login(email, password).body()
            val user = users?.firstOrNull { it.password == password }

            if (user != null) {
                val token = UUID.randomUUID().toString()

                val dto = LoginResponseDto(
                    id = UUID.randomUUID().toString(),
                    status = "success",
                    message = "Login successful",
                    token = token,
                    userId = user.id
                )

                service.saveToken(dto)

                Response.success(dto)
            } else {
                Response.error(
                    401,
                    "Invalid email or password".toResponseBody("text/plain".toMediaType())
                )
            }
        }.asResource { it.toDomain() }
    }
}