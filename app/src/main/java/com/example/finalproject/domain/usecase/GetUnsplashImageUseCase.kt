package com.example.finalproject.domain.usecase

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.repository.UnsplashRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUnsplashImageUseCase @Inject constructor(
    private val repository: UnsplashRepository
) {
    suspend operator fun invoke(city: String): Flow<Resource<String>> {
        return repository.getCityImage(city)
    }
}