package com.example.finalproject.presentation.ui.screen.bookHotel.citySearch

import androidx.lifecycle.viewModelScope
import com.example.finalproject.BaseViewModel
import com.example.finalproject.domain.usecase.CitySearchUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitySearchViewModel @Inject constructor(
    private val citySearchUseCase: CitySearchUseCase
): BaseViewModel<CitySearchState, CitySearchEvent, CitySearchEffect>(CitySearchState()) {
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
        viewModelScope.launch {
            if (query.length < 2) return@launch

            updateState { copy(isLoading = true, errorMessage = null) }

            try {
                val cities = citySearchUseCase(query)
                updateState { copy(cityList = cities, isLoading = false) }
            } catch (e: Exception) {
                updateState { copy(isLoading = false, errorMessage = e.message) }
                emitEffect(CitySearchEffect.ShowSnackbar("Failed to load cities"))
            }
        }
    }
}