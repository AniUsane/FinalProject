package com.example.finalproject.di

import com.example.finalproject.BuildConfig
import com.example.finalproject.data.service.ImgBBService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImgBBModule {

    @Provides
    @Singleton
    @Named("ImgBBRetrofit")
    fun provideImgBBRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.IMGBB_BASE_URL)
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideImgBBService(@Named("ImgBBRetrofit") retrofit: Retrofit): ImgBBService {
        return retrofit.create(ImgBBService::class.java)
    }
}