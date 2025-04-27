package com.example.finalproject.domain.usecase.bookHotel

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.bookHotel.RecentSearch
import com.example.finalproject.domain.repository.bookHotel.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentSearchesUseCase @Inject constructor(
    private val repository: RecentSearchRepository
) {
    suspend operator fun invoke(userId: String): Flow<Resource<List<RecentSearch>>> {
        return repository.getRecentSearches(userId)
    }
}