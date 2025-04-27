package com.example.finalproject

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.domain.repository.auth.DataStoreRepository
import com.example.finalproject.presentation.navigation.SessionCheckerNavHost
import com.example.finalproject.presentation.ui.theme.FinalProjectTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var dataStore: DataStoreRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalProjectTheme{
                SessionCheckerNavHost(dataStore)
            }
        }
    }
}