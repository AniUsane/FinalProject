package com.example.finalproject.presentation.ui.screen.addGuide.guideScreen

import android.net.Uri


sealed class GuideEvent {
    data class LoadGuide(val guideId: String) : GuideEvent()
    data class OnDescriptionChanged(val description: String) : GuideEvent()
    data class OnUserImagesChanged(val images: List<Uri>) : GuideEvent()
}