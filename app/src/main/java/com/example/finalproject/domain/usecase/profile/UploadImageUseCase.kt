package com.example.finalproject.domain.usecase.profile

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.repository.profile.ProfileRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(apiKey: String, imagePart: MultipartBody.Part): Resource<String> {
        return repository.uploadProfileImageToImgBB(apiKey, imagePart)
    }
}