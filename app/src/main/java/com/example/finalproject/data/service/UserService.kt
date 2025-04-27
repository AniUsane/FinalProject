package com.example.finalproject.data.service

import com.example.finalproject.data.model.auth.UserDto
import com.example.finalproject.data.model.bookHotel.RecentSearchDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("recentSearches")
    suspend fun getRecentSearches(@Query("userId") userId: String): Response<List<RecentSearchDto>>

    @POST("recentSearches")
    suspend fun addRecentSearch(@Body recentSearch: RecentSearchDto): Response<RecentSearchDto>

}