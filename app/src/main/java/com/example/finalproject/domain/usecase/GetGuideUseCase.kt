package com.example.finalproject.domain.usecase

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.addGuide.Guide
import com.example.finalproject.domain.repository.GuideRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGuidesUseCase @Inject constructor(
    private val guideRepository: GuideRepository
) {
    suspend operator fun invoke(userId: String): Flow<Resource<List<Guide>>> {
        return guideRepository.getGuide(userId)
    }
}