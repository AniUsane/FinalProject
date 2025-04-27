package com.example.finalproject.presentation.ui.screen.bookHotel.citySearch

import android.util.Log.e
import androidx.lifecycle.viewModelScope
import com.example.finalproject.BaseViewModel
import com.example.finalproject.domain.usecase.bookHotel.CitySearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitySearchViewModel @Inject constructor(
    private val citySearchUseCase: CitySearchUseCase
): BaseViewModel<CitySearchState, CitySearchEvent, CitySearchEffect>(CitySearchState()) {
    private var searchJob: Job? = null

    override fun obtainEvent(event: CitySearchEvent) {
        when (event) {
            is CitySearchEvent.OnQueryChanged -> {
                updateState { copy(query = event.query) }
                searchCities(event.query)
            }
            is CitySearchEvent.OnCitySelected -> {
                viewModelScope.launch {
                    emitEffect(CitySearchEffect.NavigateBackWithResult(event.city))
                }
            }
        }
    }

    private fun searchCities(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)

            if (query.isBlank()) {
                updateState { copy(cityList = emptyList(), isLoading = false, errorMessage = null) }
                return@launch
            }

            if (query.length < 2) return@launch

            updateState { copy(isLoading = true, errorMessage = null) }

            try {
                val cities = citySearchUseCase(query)
                updateState { copy(cityList = cities, isLoading = false) }
            } catch (e: Exception) {
                updateState { copy(isLoading = false, errorMessage = e.message) }
                emitEffect(CitySearchEffect.ShowSnackBar("Failed to load cities"))
                e("CitySearchError", "Error loading cities", e)
            }
        }
    }

}