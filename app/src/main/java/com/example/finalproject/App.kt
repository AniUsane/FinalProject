package com.example.finalproject

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
     //   Places.initialize(this, BuildConfig.GOOGLE_PLACES_API_KEY)
    }
}