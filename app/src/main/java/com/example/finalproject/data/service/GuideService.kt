package com.example.finalproject.data.service

import com.example.finalproject.data.model.addGuide.GuideDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface GuideService {
    @POST("guide")
    suspend fun createGuide(@Body guideDto: GuideDto): Response<GuideDto>

    @GET("guide")
    suspend fun getGuides(@Query("userId") userId: String): Response<List<GuideDto>>

    @PUT("guides/{id}")
    suspend fun updateGuide(@Path("id") id: String, @Body guideDto: GuideDto): Response<GuideDto>

    @GET("guides/{id}")
    suspend fun getGuideById(@Path("id") id: String): Response<GuideDto>
}