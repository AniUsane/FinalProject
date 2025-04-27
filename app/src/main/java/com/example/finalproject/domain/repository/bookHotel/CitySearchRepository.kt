package com.example.finalproject.domain.repository.bookHotel

import com.example.finalproject.domain.model.bookHotel.City

interface CitySearchRepository {
    suspend fun searchCities(query: String): List<City>
}