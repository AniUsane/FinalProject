package com.example.finalproject.data.service

import com.example.finalproject.data.model.LoginResponseDto
import com.example.finalproject.data.model.RegisterResponseDto
import com.example.finalproject.data.model.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @GET("users")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<List<UserDto>>

    @POST("users")
    suspend fun register(
        @Body user: UserDto
    ): Response<UserDto>

    @GET
    suspend fun getLoginResponse(): Response<List<LoginResponseDto>>

    @GET
    suspend fun getRegisterResponse(): Response<List<RegisterResponseDto>>
}