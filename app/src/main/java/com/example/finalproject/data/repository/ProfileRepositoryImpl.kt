package com.example.finalproject.data.repository

import com.example.finalproject.common.HandleResponse
import com.example.finalproject.common.Resource
import com.example.finalproject.data.mapper.asResource
import com.example.finalproject.data.mapper.toDomain
import com.example.finalproject.data.mapper.toDto
import com.example.finalproject.data.service.ImgBBService
import com.example.finalproject.data.service.ProfileService
import com.example.finalproject.domain.model.Profile
import com.example.finalproject.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val service: ProfileService,
    private val imgBBService: ImgBBService,
    private val handleResponse: HandleResponse
): ProfileRepository {
    override suspend fun getProfileByUserId(userId: String): Flow<Resource<Profile>> = flow {
        emit(Resource.Loading(true))
        try {
            val result = service.getProfileByUserId(userId)
            val profileList = result.body() ?: throw Exception("Empty response")
            val domainProfile = profileList.firstOrNull()?.toDomain() ?: throw Exception("Profile not found")
            emit(Resource.Success(domainProfile))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Something went wrong"))
        }
        emit(Resource.Loading(false))
    }

    override suspend fun updateProfile(profile: Profile): Flow<Resource<Profile>> {
        return handleResponse.safeApiCall {
            service.updateProfile(profile.id, profile.toDto())
        }.asResource { it.toDomain() }
    }

    override suspend fun deleteProfile(profileId: String): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            service.deleteProfile(profileId)
        }.asResource {}
    }

    override suspend fun uploadProfileImageToImgBB(
        apiKey: String,
        image: MultipartBody.Part
    ): Resource<String> {
        return try {
            val response = imgBBService.uploadImage(apiKey, image)
            if (response.success) {
                Resource.Success(response.data.url)
            } else {
                Resource.Error("Upload failed with status: ${response.status}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error during upload")
        }    }

    override suspend fun getAccProfileUserId(userId: String): Profile? {
        return try {
            val result = service.getProfileByUserId(userId)
            result.body()?.firstOrNull()?.toDomain()
        } catch (e: Exception) {
            null
        }
    }
}