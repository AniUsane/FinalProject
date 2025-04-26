package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.model.City
import com.example.finalproject.domain.repository.CitySearchRepository
import javax.inject.Inject

class CitySearchUseCase @Inject constructor(
    private val citySearchRepository: CitySearchRepository
) {
    suspend operator fun invoke(query: String): List<City>{
        return citySearchRepository.searchCities(query)
    }
}