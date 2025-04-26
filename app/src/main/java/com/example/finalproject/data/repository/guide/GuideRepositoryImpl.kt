package com.example.finalproject.data.repository.guide

import com.example.finalproject.common.utils.Resource
import com.example.finalproject.common.utils.mapResource
import com.example.finalproject.common.utils.ApiHelper
import com.example.finalproject.data.mapper.toDomain
import com.example.finalproject.data.service.guide.PopularDestinationsApiService
import com.example.finalproject.data.service.guide.UserGuideApiService
import com.example.finalproject.data.service.guide.WeekendTripApiService
import com.example.finalproject.domain.model.guide.PopularDestination
import com.example.finalproject.domain.model.guide.UserGuide
import com.example.finalproject.domain.model.guide.WeekendTrip
import com.example.finalproject.domain.repository.guide.GuideRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GuideRepositoryImpl @Inject constructor(
    private val userGuideApiService: UserGuideApiService,
    private val weekendTripApiService: WeekendTripApiService,
    private val popularDestinationsApiService: PopularDestinationsApiService,
    private val apiHelper: ApiHelper
) : GuideRepository{

    override suspend fun fetchUserGuides(): Flow<Resource<List<UserGuide>>> {


        return apiHelper.handleHttpRequest {
            userGuideApiService.fetchGuides()
        }.mapResource { dtoUserGuides ->
            dtoUserGuides.map { it.toDomain() }

        }
    }

    override suspend fun fetchWeekendTrip(): Flow<Resource<List<WeekendTrip>>> {
        return apiHelper.handleHttpRequest {
            weekendTripApiService.fetchWeekendTrip()
        }.mapResource { dtoWeekendTrip ->
            dtoWeekendTrip.map { it.toDomain() }

        }
    }

    override suspend fun fetchPopularDestination(): Flow<Resource<List<PopularDestination>>> {
        return apiHelper.handleHttpRequest {
            popularDestinationsApiService.fetchPopularDestination()
        }.mapResource { dtoPopularDestination ->
            dtoPopularDestination.map { it.toDomain() }

        }
    }
}