package com.example.finalproject.data.repository.bookHotel

import com.example.finalproject.common.HandleResponse
import com.example.finalproject.common.Resource
import com.example.finalproject.data.mapper.asResource
import com.example.finalproject.data.mapper.toDomain
import com.example.finalproject.data.model.bookHotel.RecentSearchDto
import com.example.finalproject.data.service.UserService
import com.example.finalproject.domain.model.bookHotel.RecentSearch
import com.example.finalproject.domain.repository.bookHotel.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class RecentSearchRepositoryImpl @Inject constructor(
    private val service: UserService,
    private val handleResponse: HandleResponse
) : RecentSearchRepository {

    override suspend fun getRecentSearches(userId: String): Flow<Resource<List<RecentSearch>>> {
        return handleResponse.safeApiCall {
            service.getRecentSearches(userId)
        }.asResource { dtoList ->
            dtoList.map { it.toDomain() }
        }
    }

    override suspend fun addRecentSearch(recentSearch: RecentSearch): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            service.addRecentSearch(
                RecentSearchDto(
                    id = UUID.randomUUID().toString(),
                    searchQuery = recentSearch.searchQuery,
                    userId = recentSearch.userId,
                    checkInDate = recentSearch.checkInDate,
                    checkOutDate = recentSearch.checkOutDate,
                    guests = recentSearch.guests,
                    rooms = recentSearch.rooms
                )
            )
        }.asResource { }
    }
}