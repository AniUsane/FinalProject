package com.example.finalproject.di

import com.example.finalproject.BuildConfig
import com.example.finalproject.data.repository.bookHotel.CitySearchRepositoryImpl
import com.example.finalproject.data.service.CitySearchService
import com.example.finalproject.data.service.OAuthService
import com.example.finalproject.domain.repository.bookHotel.CitySearchRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BookHotelModule {

    @Provides
    @Singleton
    @Named("OAuthToken")
    fun provideOAuthRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_FOR_TOKEN)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideOAuthService(@Named("OAuthToken") retrofit: Retrofit): OAuthService {
        return retrofit.create(OAuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideCitySearchService(@Named("OAuthToken") retrofit: Retrofit): CitySearchService {
        return retrofit.create(CitySearchService::class.java)
    }

    @Provides
    @Singleton
    fun provideCitySearchRepository(
        oAuthService: OAuthService,
        citySearchService: CitySearchService
    ): CitySearchRepository {
        return CitySearchRepositoryImpl(
            oAuthService = oAuthService,
            citySearchService = citySearchService
        )
    }
}