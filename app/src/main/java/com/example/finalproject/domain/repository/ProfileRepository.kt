package com.example.finalproject.domain.repository

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.Profile
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ProfileRepository {
    suspend fun getProfileByUserId(userId: String): Flow<Resource<Profile>>
    suspend fun updateProfile(profile: Profile): Flow<Resource<Profile>>
    suspend fun deleteProfile(profileId: String): Flow<Resource<Unit>>
    suspend fun uploadProfileImageToImgBB(apiKey: String, image: MultipartBody.Part): Resource<String>
}