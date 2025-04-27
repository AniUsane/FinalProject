package com.example.finalproject.data.model.bookHotel

import kotlinx.serialization.Serializable

@Serializable
data class CitySearchResponseDto(
    val data: List<DataObjectDto>
)

@Serializable
data class DataObjectDto(
    val type: String,
    val subType: String,
    val name: String,
    val iataCode: String? = null,
    val address: AddressDto? = null
)

@Serializable
data class AddressDto(
    val countryName: String? = null
)