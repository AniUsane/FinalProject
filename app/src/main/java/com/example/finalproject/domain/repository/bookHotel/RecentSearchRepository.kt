package com.example.finalproject.domain.repository.bookHotel

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.bookHotel.RecentSearch
import kotlinx.coroutines.flow.Flow

interface RecentSearchRepository {
    suspend fun getRecentSearches(userId: String): Flow<Resource<List<RecentSearch>>>
    suspend fun addRecentSearch(recentSearch: RecentSearch): Flow<Resource<Unit>>
}