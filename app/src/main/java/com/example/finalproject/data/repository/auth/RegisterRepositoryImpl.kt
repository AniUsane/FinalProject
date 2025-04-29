package com.example.finalproject.data.repository

import com.example.finalproject.common.HandleResponse
import com.example.finalproject.common.Resource
import com.example.finalproject.data.mapper.asResource
import com.example.finalproject.data.mapper.toDomain
import com.example.finalproject.data.mapper.toDto
import com.example.finalproject.data.model.profile.ProfileDto
import com.example.finalproject.data.service.AuthService
import com.example.finalproject.data.service.ProfileService
import com.example.finalproject.domain.model.profile.User
import com.example.finalproject.domain.repository.auth.RegisterRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val service: AuthService,
    private val profileService: ProfileService,
    private val handleResponse: HandleResponse
): RegisterRepository {
    override suspend fun register(user: User): Flow<Resource<User>> {
        return handleResponse.safeApiCall {
            val existingUsers = service.login(user.email, "").body()

            val emailExists = existingUsers?.any { it.email == user.email } == true

            if (emailExists) {
                retrofit2.Response.error(
                    409,
                    "User with this email already exists".toResponseBody("text/plain".toMediaType())
                )
            } else {
                val userResponse = service.register(user.toDto())
                val registeredUser = userResponse.body() ?: throw Exception("Registration failed")

                val profileDto = ProfileDto(
                    id = "",
                    userId = registeredUser.id,
                    username = registeredUser.email.substringBefore("@"),
                    fullName = registeredUser.fullName ?: "",
                    profileImageUrl = null,
                    bio = "",
                    trips = emptyList(),
                    guide = emptyList()
                )

                profileService.createProfile(profileDto)
                userResponse
            }
        }.asResource {
            it.toDomain()
        }
    }
}