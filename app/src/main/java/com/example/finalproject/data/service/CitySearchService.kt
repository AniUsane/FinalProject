package com.example.finalproject.data.service

import com.example.finalproject.data.model.bookHotel.CitySearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CitySearchService {
    @GET("v1/reference-data/locations/cities")
    suspend fun searchCities(
        @Header("Authorization") authorization: String,
        @Query("keyword") keyword: String,
        @Query("max") limit: Int = 50
    ): CitySearchResponseDto
}