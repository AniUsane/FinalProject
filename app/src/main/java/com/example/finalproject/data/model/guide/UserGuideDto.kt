package com.example.finalproject.data.model.guide


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserGuideDto (
    val title: String,
    val username: String,
    val content: String,
    val summary: String,
    val date: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("user_image_url")val userImageUrl: String,
    val id: Int
)