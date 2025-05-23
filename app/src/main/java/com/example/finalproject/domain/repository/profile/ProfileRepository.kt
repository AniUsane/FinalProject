package com.example.finalproject.domain.repository.profile

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.profile.Profile
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ProfileRepository {
    suspend fun getProfileByUserId(userId: String): Flow<Resource<Profile>>
    suspend fun updateProfile(profile: Profile): Flow<Resource<Profile>>
    suspend fun deleteProfile(profileId: String): Flow<Resource<Unit>>
    suspend fun uploadProfileImageToImgBB(apiKey: String, image: MultipartBody.Part): Resource<String>
    suspend fun getAccProfileUserId(userId: String): Profile?
}