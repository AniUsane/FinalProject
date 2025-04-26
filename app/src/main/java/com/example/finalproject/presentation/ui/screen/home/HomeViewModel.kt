package com.example.finalproject.presentation.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.common.utils.Resource
import com.example.finalproject.domain.usecase.guide.FetchPopularDestinationUseCase
import com.example.finalproject.domain.usecase.guide.FetchUsersGuideUseCase
import com.example.finalproject.domain.usecase.guide.FetchWeekendTripUseCase
import com.example.finalproject.presentation.mapper.toPresentation
import com.example.finalproject.presentation.ui.screen.home.state.PopularDestinationState
import com.example.finalproject.presentation.ui.screen.home.state.UserGuideState
import com.example.finalproject.presentation.ui.screen.home.state.WeekendTripState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchUsersGuideUseCase: FetchUsersGuideUseCase,
    private val fetchWeekendTripUseCase: FetchWeekendTripUseCase,
    private val fetchPopularDestinationUseCase: FetchPopularDestinationUseCase
) : ViewModel() {

    init {
        Log.d("ViewModel", "UserGuidesViewModel created")
    }
    private val _userGuidesState = MutableStateFlow<UserGuideState>(UserGuideState.Idle)
    val userGuidesState: StateFlow<UserGuideState> get() = _userGuidesState.asStateFlow()

    private val _weekendTripState = MutableStateFlow<WeekendTripState>(WeekendTripState.Idle)
    val weekendTripState: StateFlow<WeekendTripState> get() = _weekendTripState.asStateFlow()

    private val _popularDestinationState =
        MutableStateFlow<PopularDestinationState>(PopularDestinationState.Idle)
    val popularDestinationState: StateFlow<PopularDestinationState> get() = _popularDestinationState.asStateFlow()


    fun fetchUserGuides() {
        viewModelScope.launch {

            fetchUsersGuideUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loader -> {
                        _userGuidesState.value = UserGuideState.IsLoading

                    }

                    is Resource.Success -> {
                        _userGuidesState.value = UserGuideState.Success(usersGuides = resource.data)

                    }

                    is Resource.Error -> {
                        _userGuidesState.value = UserGuideState.Error(message = resource.message)
                    }
                }

            }


            fetchWeekendTripUseCase.invoke().collect { resource ->
                when (resource) {
                    is Resource.Loader -> {
                        _weekendTripState.value = WeekendTripState.IsLoading

                    }

                    is Resource.Success -> {
                        _weekendTripState.value =
                            WeekendTripState.Success(weekendTrips = resource.data.map { it.toPresentation() })

                    }

                    is Resource.Error -> {
                        _weekendTripState.value = WeekendTripState.Error(message = resource.message)
                    }
                }

            }

            fetchPopularDestinationUseCase.invoke().collect { resource ->
                when (resource) {
                    is Resource.Loader -> {
                        _popularDestinationState.value =
                            PopularDestinationState.IsLoading
                    }

                    is Resource.Success -> {
                        _popularDestinationState.value =
                            PopularDestinationState.Success(popularDestinations = resource.data.map { it.toPresentation() })

                    }

                    is Resource.Error -> {
                        _popularDestinationState.value =
                            PopularDestinationState.Error(message = resource.message)
                    }
                }

            }


        }


    }
}