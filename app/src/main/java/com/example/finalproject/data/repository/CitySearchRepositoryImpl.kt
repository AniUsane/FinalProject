package com.example.finalproject.data.repository

import com.example.finalproject.BuildConfig
import com.example.finalproject.data.mapper.toDomain
import com.example.finalproject.data.service.CitySearchService
import com.example.finalproject.data.service.OAuthService
import com.example.finalproject.domain.model.City
import com.example.finalproject.domain.repository.CitySearchRepository
import javax.inject.Inject
import javax.inject.Named

class CitySearchRepositoryImpl @Inject constructor(
    @Named("OAuthToken") private val oAuthService: OAuthService,
    @Named("OAuthToken") private val citySearchService: CitySearchService
):CitySearchRepository {

    private var accessToken: String? = null
    private var tokenExpiryTime: Long = 0

    override suspend fun searchCities(query: String): List<City> {
        if (accessToken == null || System.currentTimeMillis() > tokenExpiryTime) {
            refreshToken()
        }

        val bearer = "Bearer $accessToken"
        val response = citySearchService.searchCities(
            authorization = bearer,
            keyword = query
        )

        return response.data.map { it.toDomain() }
    }

    private suspend fun refreshToken() {
        val tokenResponse = oAuthService.getAccessToken(
            clientId = BuildConfig.AMADEUS_API_KEY,
            clientSecret = BuildConfig.AMADEUS_API_SECRET
        )
        accessToken = tokenResponse.accessToken
        tokenExpiryTime = System.currentTimeMillis() + (tokenResponse.expiresIn * 1000)
    }
}