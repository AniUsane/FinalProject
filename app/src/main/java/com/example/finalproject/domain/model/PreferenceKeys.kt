package com.example.finalproject.domain.model

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val TOKEN_KEY = stringPreferencesKey("auth_token")
    val USER_ID_KEY = stringPreferencesKey("user_id")
}