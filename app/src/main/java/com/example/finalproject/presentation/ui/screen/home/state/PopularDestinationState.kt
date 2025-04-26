package com.example.finalproject.presentation.ui.screen.home.state

import com.example.finalproject.presentation.model.guide.PopularDestinationUi

sealed class PopularDestinationState {
    data object IsLoading: PopularDestinationState()
    data object Idle : PopularDestinationState()
    data class Success(val popularDestinations: List<PopularDestinationUi>) : PopularDestinationState()
    data class Error(val message : String) : PopularDestinationState()
}