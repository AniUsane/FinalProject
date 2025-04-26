package com.example.finalproject.domain.repository

import com.example.finalproject.domain.model.City

interface CitySearchRepository {
    suspend fun searchCities(query: String): List<City>
}