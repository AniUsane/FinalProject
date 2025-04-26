package com.example.finalproject.domain.usecase.guide

import com.example.finalproject.common.utils.Resource
import com.example.finalproject.domain.model.guide.PopularDestination
import com.example.finalproject.domain.repository.guide.GuideRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchPopularDestinationUseCase @Inject constructor (private val guideRepository: GuideRepository) {
    suspend operator fun invoke() : Flow<Resource<List<PopularDestination>>> {
        return guideRepository.fetchPopularDestination()
    }
}