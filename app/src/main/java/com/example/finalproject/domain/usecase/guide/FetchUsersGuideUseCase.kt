package com.example.finalproject.domain.usecase.guide

import com.example.finalproject.common.utils.Resource
import com.example.finalproject.domain.model.guide.UserGuide
import com.example.finalproject.domain.repository.guide.GuideRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchUsersGuideUseCase @Inject constructor (private val guideRepository: GuideRepository) {
    suspend operator fun invoke() : Flow<Resource<List<UserGuide>>> {
        return guideRepository.fetchUserGuides()
    }
}