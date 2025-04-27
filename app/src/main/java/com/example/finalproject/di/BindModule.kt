package com.example.finalproject.di

import com.example.finalproject.data.repository.dataStore.DataStoreRepositoryImpl
import com.example.finalproject.domain.repository.auth.DataStoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    @Binds
    @Singleton
    abstract fun bindDataStoreRepository(
        impl: DataStoreRepositoryImpl
    ): DataStoreRepository
}