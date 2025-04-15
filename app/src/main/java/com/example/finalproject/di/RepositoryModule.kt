package com.example.finalproject.di

import com.example.finalproject.common.HandleResponse
import com.example.finalproject.data.repository.auth.LoginRepositoryImpl
import com.example.finalproject.data.repository.auth.RegisterRepositoryImpl
import com.example.finalproject.data.service.AuthService
import com.example.finalproject.domain.repository.LoginRepository
import com.example.finalproject.domain.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideLoginRepository(service: AuthService, handleResponse: HandleResponse): LoginRepository {
        return LoginRepositoryImpl(service, handleResponse)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(service: AuthService, handleResponse: HandleResponse): RegisterRepository {
        return RegisterRepositoryImpl(service, handleResponse)
    }
}

