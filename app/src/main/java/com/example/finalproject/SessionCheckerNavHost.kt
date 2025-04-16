package com.example.finalproject

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.data.repository.PreferenceKeys
import com.example.finalproject.domain.repository.DataStoreRepository
import com.example.finalproject.presentation.navigation.AppNavGraph
import kotlinx.coroutines.flow.first

@Composable
fun SessionCheckerNavHost(dataStore: DataStoreRepository) {
    val navController = rememberNavController()
    var startDestination by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val token = dataStore.readString(PreferenceKeys.TOKEN_KEY).first()
        val userId = dataStore.readString(PreferenceKeys.USER_ID_KEY).first()

        startDestination = if (token.isNotBlank() && userId.isNotBlank()) {
            "profile"
        } else {
            "login"
        }
    }

    if (startDestination == null) {
        // Show loading splash while determining session
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        AppNavGraph(navController = navController, startDestination = startDestination!!)
    }
}