package com.example.finalproject.di

import com.example.finalproject.domain.repository.auth.LoginRepository
import com.example.finalproject.domain.repository.auth.RegisterRepository
import com.example.finalproject.domain.usecase.auth.LoginUseCase
import com.example.finalproject.domain.usecase.auth.RegisterUseCase
import com.example.finalproject.domain.usecase.auth.ValidateEmailUseCase
import com.example.finalproject.domain.usecase.auth.ValidatePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCase(loginRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(registerRepository: RegisterRepository): RegisterUseCase {
        return RegisterUseCase(registerRepository)
    }

    @Provides
    @Singleton
    fun provideValidateEmailUseCase(): ValidateEmailUseCase = ValidateEmailUseCase()

    @Provides
    @Singleton
    fun providePasswordUseCase(): ValidatePasswordUseCase = ValidatePasswordUseCase()
}