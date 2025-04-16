package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.model.Profile
import com.example.finalproject.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(profile: Profile) = repository.updateProfile(profile)
}