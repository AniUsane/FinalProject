package com.example.finalproject.data.repository

import com.example.finalproject.common.HandleResponse
import com.example.finalproject.common.Resource
import com.example.finalproject.data.mapper.asResource
import com.example.finalproject.data.service.UnsplashService
import com.example.finalproject.domain.repository.UnsplashRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnsplashRepositoryImpl @Inject constructor(
    private val service: UnsplashService,
    private val handleResponse: HandleResponse
) : UnsplashRepository {

    override suspend fun getCityImage(cityName: String): Flow<Resource<String>> {
        return handleResponse.safeApiCall {
            service.searchPhotos(query = cityName)
        }.asResource { response ->
            response.results.firstOrNull()?.urls?.regular
                ?: throw IllegalStateException("No image found")
        }
    }
}
