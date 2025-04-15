package com.example.finalproject.presentation.ui.screen.home.state

import com.example.finalproject.domain.model.guide.UserGuide


sealed class UserGuideState {
    data object IsLoading: UserGuideState()
    data object Idle : UserGuideState()
    data class Success(val usersGuides: List<UserGuide>) : UserGuideState()
    data class Error(val message : String) : UserGuideState()
}
