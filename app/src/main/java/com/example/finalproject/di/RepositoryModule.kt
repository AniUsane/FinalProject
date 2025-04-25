package com.example.finalproject.di

import com.example.finalproject.common.HandleResponse
import com.example.finalproject.data.repository.LoginRepositoryImpl
import com.example.finalproject.data.repository.ProfileRepositoryImpl
import com.example.finalproject.data.repository.RegisterRepositoryImpl
import com.example.finalproject.data.repository.UserRepositoryImpl
import com.example.finalproject.data.service.AuthService
import com.example.finalproject.data.service.ImgBBService
import com.example.finalproject.data.service.ProfileService
import com.example.finalproject.data.service.UserService
import com.example.finalproject.domain.repository.LoginRepository
import com.example.finalproject.domain.repository.ProfileRepository
import com.example.finalproject.domain.repository.RegisterRepository
import com.example.finalproject.domain.repository.UserRepository
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
    fun provideRegisterRepository(service: AuthService, profileService: ProfileService, handleResponse: HandleResponse): RegisterRepository {
        return RegisterRepositoryImpl(service, profileService, handleResponse)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(service: ProfileService, imgBBService: ImgBBService, handleResponse: HandleResponse): ProfileRepository {
        return ProfileRepositoryImpl(service, imgBBService, handleResponse)
    }

    @Provides
    @Singleton
    fun provideUserRepository(service: UserService, handleResponse: HandleResponse): UserRepository {
        return UserRepositoryImpl(service, handleResponse)
    }
}