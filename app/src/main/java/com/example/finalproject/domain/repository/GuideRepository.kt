package com.example.finalproject.domain.repository

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.addGuide.Guide
import kotlinx.coroutines.flow.Flow

interface GuideRepository {
    suspend fun createGuide(guide: Guide): Flow<Resource<Guide>>
    suspend fun getGuide(userId: String): Flow<Resource<List<Guide>>>
    suspend fun updateGuide(guide: Guide): Flow<Resource<Guide>>
}