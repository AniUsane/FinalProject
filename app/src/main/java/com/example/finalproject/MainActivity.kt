package com.example.finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.finalproject.presentation.navigation.RootNavigationGraph
import com.example.finalproject.presentation.ui.theme.FinalProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            var isAuthenticated by remember { mutableStateOf(false) }
            var toTripDestination by remember { mutableStateOf(false) }


            RootNavigationGraph(
                isAuthenticated = isAuthenticated,
                toTripDestination = toTripDestination,
                onAuthSuccess = { isAuthenticated = true },
                onToTripDestination = {toTripDestination = true}
            )

        }
    }
}