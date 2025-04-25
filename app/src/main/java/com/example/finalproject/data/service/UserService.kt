package com.example.finalproject.data.service

import com.example.finalproject.data.model.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {

    @DELETE("users/{id}")
    suspend fun deleteUser(
        @Path("id") profileId: String
    ): Response<Unit>

    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id: String, @Body user: UserDto
    ): Response<UserDto>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: String): Response<UserDto>

}