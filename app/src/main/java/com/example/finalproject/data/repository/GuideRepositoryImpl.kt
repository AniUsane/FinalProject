package com.example.finalproject.data.repository

import com.example.finalproject.common.HandleResponse
import com.example.finalproject.common.Resource
import com.example.finalproject.data.mapper.asResource
import com.example.finalproject.data.mapper.toDomain
import com.example.finalproject.data.service.GuideService
import com.example.finalproject.domain.model.addGuide.Guide
import com.example.finalproject.domain.repository.GuideRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.finalproject.data.mapper.toDto

class GuideRepositoryImpl @Inject constructor(
    private val service: GuideService,
    private val handleResponse: HandleResponse
):GuideRepository {
    override suspend fun createGuide(guide: Guide): Flow<Resource<Guide>> {
        return handleResponse.safeApiCall {
            service.createGuide(guide.toDto())
        }.asResource { it.toDomain() }
    }

    override suspend fun getGuide(userId: String): Flow<Resource<List<Guide>>> {
        return handleResponse.safeApiCall {
            service.getGuides(userId)
        }.asResource { list -> list.map { it.toDomain() } }    }

    override suspend fun updateGuide(guide: Guide): Flow<Resource<Guide>> {
        return handleResponse.safeApiCall {
            service.updateGuide(guide.id, guide.toDto())
        }.asResource { it.toDomain() }    }

}