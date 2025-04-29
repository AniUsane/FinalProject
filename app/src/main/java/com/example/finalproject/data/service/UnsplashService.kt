package com.example.finalproject.data.service

import com.example.finalproject.BuildConfig
import com.example.finalproject.data.model.unsplash.UnsplashSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashService {
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 1,
        @Query("client_id") apiKey: String = BuildConfig.UNSPLASH_ACCESS_KEY
    ): Response<UnsplashSearchResponse>
}