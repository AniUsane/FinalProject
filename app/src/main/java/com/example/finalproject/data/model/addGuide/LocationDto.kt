package com.example.finalproject.data.model.addGuide

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val id: String,
    val name: String,
    val type: String,
    val country: String
)