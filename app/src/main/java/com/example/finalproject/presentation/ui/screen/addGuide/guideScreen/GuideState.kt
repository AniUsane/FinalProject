package com.example.finalproject.presentation.ui.screen.addGuide.guideScreen

import com.example.finalproject.presentation.model.addGuide.GuideUi

data class GuideState(
    val guide: GuideUi? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
