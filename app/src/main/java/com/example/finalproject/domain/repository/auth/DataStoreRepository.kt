package com.example.finalproject.domain.repository.auth

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveString(key: Preferences.Key<String>, value: String)
    fun readString(key: Preferences.Key<String>): Flow<String>
    suspend fun clear()
}