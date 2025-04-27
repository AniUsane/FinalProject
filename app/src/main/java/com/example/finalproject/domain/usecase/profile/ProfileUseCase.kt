package com.example.finalproject.domain.usecase.profile

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.profile.Profile
import com.example.finalproject.domain.repository.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(userId: String): Flow<Resource<Profile>> {
        return repository.getProfileByUserId(userId)
    }
}