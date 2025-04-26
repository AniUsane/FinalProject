package com.example.finalproject.domain.usecase.profile

import com.example.finalproject.domain.repository.DataStoreRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val dataStore: DataStoreRepository
) {
    suspend operator fun invoke(){
        dataStore.clear()
    }
}