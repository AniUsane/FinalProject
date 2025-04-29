package com.example.finalproject.domain.usecase

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.addGuide.Guide
import com.example.finalproject.domain.repository.GuideRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateGuideUseCase @Inject constructor(
    private val repository: GuideRepository
) {
    suspend operator fun invoke(guide: Guide): Flow<Resource<Guide>> {
        return repository.updateGuide(guide)
    }
}