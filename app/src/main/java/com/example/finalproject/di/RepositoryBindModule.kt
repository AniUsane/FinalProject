package com.example.finalproject.di

import com.example.finalproject.data.repository.guide.GuideRepositoryImpl
import com.example.finalproject.domain.repository.guide.GuideRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindModule {
    @Binds
    abstract fun bindGuideRepository(
        guideRepositoryImpl: GuideRepositoryImpl
    ) : GuideRepository
}