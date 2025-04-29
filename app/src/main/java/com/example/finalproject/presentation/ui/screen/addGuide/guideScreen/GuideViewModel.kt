package com.example.finalproject.presentation.ui.screen.addGuide.guideScreen

import android.net.Uri
import android.util.Log.d
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.finalproject.BaseViewModel
import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.addGuide.Guide
import com.example.finalproject.domain.usecase.GetGuideByIdUseCase
import com.example.finalproject.domain.usecase.GetUnsplashImageUseCase
import com.example.finalproject.domain.usecase.UpdateGuideUseCase
import com.example.finalproject.presentation.mapper.toDomain
import com.example.finalproject.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuideViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getGuideUseCase: GetGuideByIdUseCase,
    private val updateGuideUseCase: UpdateGuideUseCase,
    private val getUnsplashImageUseCase: GetUnsplashImageUseCase
) : BaseViewModel<GuideState, GuideEvent, GuideEffect>(GuideState()) {

    init {
        val guideId = savedStateHandle.get<String>("guideId")
        d("GuideViewModel", "guideId = $guideId")
        if (guideId != null) {
            obtainEvent(GuideEvent.LoadGuide(guideId))
        }
    }

    override fun obtainEvent(event: GuideEvent) {
        when (event) {
            is GuideEvent.LoadGuide -> loadGuide(event.guideId)
            is GuideEvent.OnDescriptionChanged -> updateDescription(event.description)
            is GuideEvent.OnUserImagesChanged -> {
                viewModelScope.launch { updateUserImages(event.images) }
            }
        }
    }

    private fun loadGuide(guideId: String) {
        d("GuideViewModel", "Loading guide with id = $guideId")
        viewModelScope.launch {
            updateState { copy(isLoading = true) }

            getGuideUseCase(guideId).collect { result ->
                when (result) {
                    is Resource.Success<*> -> {
                        val guide = (result as Resource.Success<Guide>).data.toPresentation()

                        // Fetch Unsplash image and inject if empty
                        val unsplashUrl = getUnsplashImageUseCase(guide.location)
                            .firstOrNull { it is Resource.Success }
                            ?.let { (it as Resource.Success).data }
                            ?: ""
                        val finalGuide = guide.copy(data = guide.data.copy(imageUrl = unsplashUrl))

                        updateState { copy(guide = finalGuide, isLoading = false) }
                    }
                    is Resource.Error -> {
                        updateState { copy(errorMessage = result.errorMessage, isLoading = false) }
                        emitEffect(GuideEffect.ShowSnackBar(result.errorMessage))
                    }
                    is Resource.Loading -> updateState { copy(isLoading = result.loading) }
                }
            }
        }
    }

    private fun updateDescription(newDesc: String) {
        val guide = viewState.value.guide ?: return
        val updatedGuide = guide.copy(data = guide.data.copy(description = newDesc))

        updateState { copy(guide = updatedGuide) }

        viewModelScope.launch {
            updateGuideUseCase(updatedGuide.toDomain()).collect{}
        }
    }

    private suspend fun updateUserImages(newUris: List<Uri>) {
        val guide = viewState.value.guide ?: return
        val uploadedUrls = newUris.map { uri -> uploadImage(uri) }

        val updatedGuide = guide.copy(data = guide.data.copy(userImages = uploadedUrls))

        updateState { copy(guide = updatedGuide) }

        viewModelScope.launch {
            updateGuideUseCase(updatedGuide.toDomain()).collect{}
        }
    }

    private suspend fun uploadImage(uri: Uri): String {
        return "https://fake.image.url/${uri.lastPathSegment}"
    }
}