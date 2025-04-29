package com.example.finalproject.presentation.ui.screen.addGuide.foraefj

import android.net.Uri

data class AddGuideState(
    val title: String = "",
    val description: String = "",
    val city: String = "",
    val imageUri: Uri? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userImages: List<Uri> = emptyList()
)
