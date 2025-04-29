package com.example.finalproject.di

import com.example.finalproject.BuildConfig
import com.example.finalproject.data.service.UnsplashService
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
object UnsplashModule {

    @Singleton
    @Provides
    @Named("UnsplashClient")
    fun provideUnsplashClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_UNSPLASH)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Singleton
    @Provides
    fun provideUnsplashService(@Named("UnsplashClient") retrofit: Retrofit): UnsplashService {
        return retrofit.create(UnsplashService::class.java)
    }
}