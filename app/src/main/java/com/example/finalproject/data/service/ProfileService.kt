package com.example.finalproject.data.service

import com.example.finalproject.data.model.ProfileDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileService {

    @GET("Profile")
    suspend fun getProfileByUserId(@Query("userId") userId: String):
            Response<List<ProfileDto>>

    @POST("Profile")
    suspend fun createProfile(@Body profile: ProfileDto):
            Response<ProfileDto>

    @PUT("Profile/{id}")
    suspend fun updateProfile(@Path("id") profileId: String, @Body profile: ProfileDto):
            Response<ProfileDto>

    @DELETE("Profile/{id}")
    suspend fun deleteProfile(@Path("id") profileId: String): Response<Unit>
}