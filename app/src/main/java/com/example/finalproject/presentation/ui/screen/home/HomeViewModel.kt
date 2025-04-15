package com.example.tbc_final.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.common.utils.Resource
import com.example.finalproject.domain.usecase.guide.FetchUsersGuideUseCase
import com.example.finalproject.presentation.ui.screen.home.state.UserGuideState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchUsersGuideUseCase: FetchUsersGuideUseCase
) : ViewModel() {
    private val _userGuidesState = MutableStateFlow<UserGuideState>(UserGuideState.Idle)
    val userGuidesState: StateFlow<UserGuideState> get()  = _userGuidesState.asStateFlow()



     fun fetchUserGuides() {
        viewModelScope.launch {

            fetchUsersGuideUseCase.invoke().collect{ resource ->
                when (resource) {
                    is Resource.Loader  -> {

                    }
                    is Resource.Success -> {
                        _userGuidesState.value = UserGuideState.Success(usersGuides = resource.data)
                        Log.d("ViewModel", "State: ${_userGuidesState.value}")

                    }
                    is Resource.Error -> {
                        _userGuidesState.value = UserGuideState.Error(message = resource.message)
                    }
                }

            }
        }
    }
}