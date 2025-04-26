package com.example.finalproject.data.model.profile

import kotlinx.serialization.Serializable

@Serializable
data class ImgBBUploadResponse(
    val data: ImgBBData,
    val success: Boolean,
    val status: Int
)

@Serializable
data class ImgBBData(
    val url: String
)
