package com.example.finalproject.data.service.guide

import com.example.finalproject.data.model.guide.UserGuideDto
import retrofit2.Response
import retrofit2.http.GET

interface UserGuideApiService {
    @GET("guides")
    suspend fun fetchGuides() : Response<List<UserGuideDto>>
}