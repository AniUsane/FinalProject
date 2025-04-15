package com.example.finalproject.data.repository.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.finalproject.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): DataStoreRepository {
    override suspend fun saveString(key: Preferences.Key<String>, value: String) {
        dataStore.edit { settings ->
            settings[key] = value
        }
    }

    override fun readString(key: Preferences.Key<String>) = dataStore.data
        .map { preferences ->
            preferences[key] ?: ""
        }

    override suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }
}