package com.example.finalproject.di

import com.example.finalproject.data.service.guide.PopularDestinationsApiService
import com.example.finalproject.data.service.guide.UserGuideApiService
import com.example.finalproject.data.service.guide.WeekendTripApiService
import com.example.finalproject.di.qualifier.Guide
import com.example.finalproject.domain.repository.guide.GuideRepository
import com.example.finalproject.domain.usecase.guide.FetchUsersGuideUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://67ee8693c11d5ff4bf79ebdf.mockapi.io/final/"

    @Provides
    @Guide
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(myHttpClient())
            .build()
    }

    @Provides
    @Singleton
    fun provideGuideApiService(@Guide retrofit: Retrofit): UserGuideApiService {
        return retrofit.create(UserGuideApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeekendTripApiService(@Guide retrofit: Retrofit): WeekendTripApiService {
        return retrofit.create(WeekendTripApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePopularDestinationApiService(@Guide retrofit: Retrofit): PopularDestinationsApiService {
        return retrofit.create(PopularDestinationsApiService::class.java)
    }

    @Provides
    fun provideFetchUserGuidesUseCase(guideRepository: GuideRepository): FetchUsersGuideUseCase {
        return FetchUsersGuideUseCase(guideRepository   )
    }

    class MyInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            val response = chain.proceed(request)
            return response
        }
    }

    private fun myHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val builder = OkHttpClient().newBuilder()
            .addInterceptor(MyInterceptor())
            .addInterceptor(loggingInterceptor)
        return builder.build()
    }

    }