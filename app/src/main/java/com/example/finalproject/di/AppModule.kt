package com.example.finalproject.di

import com.example.finalproject.BuildConfig
import com.example.finalproject.common.HandleResponse
import com.example.finalproject.common.utils.ApiHelper
import com.example.finalproject.data.service.AuthService
import com.example.finalproject.data.service.ProfileService
import com.example.finalproject.data.service.UserService
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
object AppModule {

    @Singleton
    @Provides
    @Named("RetrofitClient")
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthService(@Named("RetrofitClient") retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserService(@Named("RetrofitClient") retrofit: Retrofit): UserService {
        return  retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileService(@Named("RetrofitClient") retrofit: Retrofit): ProfileService {
        return retrofit.create(ProfileService::class.java)
    }

    @Provides
    fun provideHandleResponse(): HandleResponse {
        return HandleResponse()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideApiHelper(): ApiHelper {
        return ApiHelper
    }

}