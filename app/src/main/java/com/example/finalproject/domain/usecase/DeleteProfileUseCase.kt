package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.repository.ProfileRepository
import javax.inject.Inject

class DeleteProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(profileId: String) = repository.deleteProfile(profileId)
}