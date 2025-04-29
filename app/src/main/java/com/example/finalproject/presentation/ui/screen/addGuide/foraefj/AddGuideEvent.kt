package com.example.finalproject.presentation.ui.screen.addGuide.foraefj

import android.net.Uri

sealed class AddGuideEvent {
    data class OnTitleChanged(val title: String) : AddGuideEvent()
    data class OnDescriptionChanged(val description: String) : AddGuideEvent()
    data object OnCityClicked : AddGuideEvent()
    data class OnCitySelected(val city: String) : AddGuideEvent()
    data object OnSubmitClicked : AddGuideEvent()
    data class OnUserImagesChanged(val images: List<Uri>) : AddGuideEvent()
}