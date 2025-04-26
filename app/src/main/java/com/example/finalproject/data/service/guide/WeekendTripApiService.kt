package com.example.finalproject.data.service.guide

import retrofit2.http.GET
import com.example.finalproject.data.model.guide.WeekendTripDto
import retrofit2.Response

interface WeekendTripApiService {
    @GET("weekendtrips")
    suspend fun fetchWeekendTrip() : Response<List<WeekendTripDto>>
}