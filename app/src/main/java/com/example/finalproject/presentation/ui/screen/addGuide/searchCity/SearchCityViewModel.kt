package com.example.finalproject.presentation.ui.screen.addGuide.searchCity

import androidx.lifecycle.viewModelScope
import com.example.finalproject.BaseViewModel
import com.example.finalproject.domain.repository.auth.DataStoreRepository
import com.example.finalproject.domain.usecase.CreateGuideUseCase
import com.example.finalproject.domain.usecase.bookHotel.CitySearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val citySearchUseCase: CitySearchUseCase,
    private val createGuideUseCase: CreateGuideUseCase,
    private val dataStoreRepository: DataStoreRepository
) : BaseViewModel<SearchCityState, SearchCityEvent, SearchCityEffect>(SearchCityState()) {

    override fun obtainEvent(event: SearchCityEvent) {
        when (event) {
            is SearchCityEvent.OnQueryChanged -> {
                updateState { copy(query = event.query) }
                searchCities()
            }
            is SearchCityEvent.OnCitySelected -> {
                updateState {
                    copy(
                        selectedCity = event.city,
                        query = event.city.name,
                        cityList = emptyList()
                    )
                }
            }
//            is SearchCityEvent.OnCreateGuideClicked -> {
//                createGuide()
//            }
        }
    }

    private fun searchCities() {
        viewModelScope.launch {
            val query = viewState.value.query

            if (query.isBlank() || query.length < 3) return@launch

            delay(300)

            updateState { copy(isLoading = true) }

            try {
                val cities = citySearchUseCase(viewState.value.query)

                updateState {
                    copy(
                        isLoading = false,
                        cityList = cities
                    )
                }
            } catch (e: Exception) {
                updateState {
                    copy(isLoading = false)
                }
                emitEffect(SearchCityEffect.ShowSnackBar(e.message ?: "Unknown error"))
            }
        }
    }

//    private fun createGuide() {
//        viewModelScope.launch {
//            val selectedCity = viewState.value.selectedCity ?: return@launch
//
//            updateState { copy(isGuideCreating = true) }
//            val userId = dataStoreRepository.readString(USER_ID_KEY).first()
//
//            val guide = GuideUi(
//                id = "",
//                userId = userId,
//                location = selectedCity.toLocationUi(),
//                data = GuideDataUi(
//                    title = "${selectedCity.name} Guide",
//                    description = "",
//                    imageUrl = null
//                ),
//                createdAt = "" // generate timestamp if needed
//            )
//
//            createGuideUseCase(guide.toDomain()).collect { result ->
//                when (result) {
//                    is Resource.Success -> {
//                        updateState { copy(isGuideCreating = false) }
//                        emitEffect(SearchCityEffect.GuideCreatedSuccessfully)
//                    }
//                    is Resource.Error -> {
//                        updateState { copy(isGuideCreating = false) }
//                        emitEffect(SearchCityEffect.ShowSnackBar(result.errorMessage))
//                    }
//                    is Resource.Loading -> updateState { copy(isGuideCreating = true) }
//                }
//            }
//        }
//    }
}