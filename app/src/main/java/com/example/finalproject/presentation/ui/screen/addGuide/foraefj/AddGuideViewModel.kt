package com.example.finalproject.presentation.ui.screen.addGuide.foraefj

import androidx.lifecycle.viewModelScope
import com.example.finalproject.BaseViewModel
import com.example.finalproject.common.Resource
import com.example.finalproject.data.repository.dataStore.PreferenceKeys.USER_ID_KEY
import com.example.finalproject.domain.repository.auth.DataStoreRepository
import com.example.finalproject.domain.usecase.CreateGuideUseCase
import com.example.finalproject.presentation.mapper.toDomain
import com.example.finalproject.presentation.mapper.toLocationUi
import com.example.finalproject.presentation.model.addGuide.GuideDataUi
import com.example.finalproject.presentation.model.addGuide.GuideUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGuideViewModel @Inject constructor(
    private val createGuideUseCase: CreateGuideUseCase,
    private val dataStoreRepository: DataStoreRepository
) : BaseViewModel<AddGuideState, AddGuideEvent, AddGuideEffect>(AddGuideState()) {

    override fun obtainEvent(event: AddGuideEvent) {
        when (event) {
            is AddGuideEvent.OnQueryChanged -> {
                updateState { copy(query = event.query) }
            }
            is AddGuideEvent.OnCitySelected -> {
                updateState { copy(
                    selectedCity = event.city, query = event.city.name
                ) }
            }
            is AddGuideEvent.OnCreateGuideClicked -> {
                createGuide()
            }
            is AddGuideEvent.OnSearchCityFieldClicked -> {
                viewModelScope.launch { emitEffect(AddGuideEffect.NavigateToSearchCityScreen) }
            }
        }
    }

    private fun createGuide() {
        viewModelScope.launch {
            val selectedCity = viewState.value.selectedCity ?: return@launch

            updateState { copy(isCreatingGuide = true) }

            val userId = dataStoreRepository.readString(USER_ID_KEY).first()

            val guide = GuideUi(
                id = "",
                userId = userId,
                location = selectedCity.toLocationUi(),
                data = GuideDataUi(
                    title = "${selectedCity.name} Guide",
                    description = "",
                    imageUrl = null
                ),
                createdAt = ""
            )

            createGuideUseCase(guide.toDomain()).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        updateState { copy(isCreatingGuide = false) }
                        emitEffect(AddGuideEffect.GuideCreatedSuccessfully)
                    }
                    is Resource.Error -> {
                        updateState { copy(isCreatingGuide = false) }
                        emitEffect(AddGuideEffect.ShowSnackBar(result.errorMessage))
                    }
                    is Resource.Loading -> updateState { copy(isCreatingGuide = true) }
                }
            }
        }
    }
}