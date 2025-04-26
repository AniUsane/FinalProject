package com.example.finalproject.data.service.guide

import com.example.finalproject.data.model.guide.WeekendTripDto
import retrofit2.Response
import retrofit2.http.GET

interface WeekendTripApiService {
    @GET("weekendtrips")
    suspend fun fetchWeekendTrip() : Response<List<WeekendTripDto>>
}