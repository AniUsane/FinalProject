package com.example.finalproject.domain.usecase.bookHotel

import com.example.finalproject.domain.model.bookHotel.City
import com.example.finalproject.domain.repository.bookHotel.CitySearchRepository
import javax.inject.Inject

class CitySearchUseCase @Inject constructor(
    private val citySearchRepository: CitySearchRepository
) {
    suspend operator fun invoke(query: String): List<City>{
        return citySearchRepository.searchCities(query)
    }
}