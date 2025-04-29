package com.example.finalproject.domain.repository

import com.example.finalproject.common.Resource
import kotlinx.coroutines.flow.Flow

interface UnsplashRepository {
    suspend fun getCityImage(cityName: String): Flow<Resource<String>>
}