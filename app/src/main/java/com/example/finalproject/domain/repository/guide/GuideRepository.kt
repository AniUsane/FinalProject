package com.example.finalproject.domain.repository.guide

import com.example.finalproject.common.utils.Resource
import com.example.finalproject.domain.model.guide.PopularDestination
import com.example.finalproject.domain.model.guide.UserGuide
import com.example.finalproject.domain.model.guide.WeekendTrip
import kotlinx.coroutines.flow.Flow

interface GuideRepository {
    suspend fun fetchUserGuides() : Flow<Resource<List<UserGuide>>>
    suspend fun fetchWeekendTrip() : Flow<Resource<List<WeekendTrip>>>
    suspend fun fetchPopularDestination() : Flow<Resource<List<PopularDestination>>>
}