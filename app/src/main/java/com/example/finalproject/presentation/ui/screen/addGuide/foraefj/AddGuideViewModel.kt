package com.example.finalproject.presentation.ui.screen.addGuide.foraefj

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.finalproject.BaseViewModel
import com.example.finalproject.common.Resource
import com.example.finalproject.data.repository.dataStore.PreferenceKeys.USER_ID_KEY
import com.example.finalproject.domain.repository.auth.DataStoreRepository
import com.example.finalproject.domain.usecase.CreateGuideUseCase
import com.example.finalproject.domain.usecase.profile.UploadImageUseCase
import com.example.finalproject.presentation.mapper.toDomain
import com.example.finalproject.presentation.model.addGuide.GuideDataUi
import com.example.finalproject.presentation.model.addGuide.GuideUi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class AddGuideViewModel @Inject constructor(
    private val createGuideUseCase: CreateGuideUseCase,
    private val dataStore: DataStoreRepository,
    private val uploadImageUseCase: UploadImageUseCase,
    @ApplicationContext private val context: Context,
) : BaseViewModel<AddGuideState, AddGuideEvent, AddGuideEffect>(AddGuideState()) {

    override fun obtainEvent(event: AddGuideEvent) {
        when (event) {
            is AddGuideEvent.OnTitleChanged -> updateState { copy(title = event.title) }
            is AddGuideEvent.OnDescriptionChanged -> updateState { copy(description = event.description) }
            is AddGuideEvent.OnCitySelected -> updateState { copy(city = event.city) }
            is AddGuideEvent.OnUserImagesChanged -> updateState {
                copy(
                    imageUri = event.images.firstOrNull(),
                    userImages = event.images
                )
            }
            is AddGuideEvent.OnSubmitClicked -> viewModelScope.launch { submitGuide() }
            is AddGuideEvent.OnCityClicked -> viewModelScope.launch {
                emitEffect(AddGuideEffect.ShowSnackBar("Opening city search..."))
            }
        }
    }

    private suspend fun submitGuide() {
        val state = viewState.value
        if (state.city.isBlank()) {
            emitEffect(AddGuideEffect.ShowSnackBar("Please select a city"))
            return
        }

        updateState { copy(isLoading = true) }
        val userId = dataStore.readString(USER_ID_KEY).first()

        val imageUrl = state.imageUri?.let { uploadToImgBB(it) }

        val guideUi = GuideUi(
            id = "",
            userId = userId,
            location = state.city,
            data = GuideDataUi(
                title = state.title,
                description = state.description,
                imageUrl = imageUrl
            ),
            createdAt = System.currentTimeMillis().toString()
        )

        val guideDomain = guideUi.toDomain()

        createGuideUseCase(guideDomain).collect { result ->
            when (result) {
                is Resource.Success -> {
                    updateState { copy(isLoading = false) }
                    emitEffect(AddGuideEffect.NavigateToProfile)
                }
                is Resource.Error -> {
                    updateState { copy(isLoading = false, errorMessage = result.errorMessage) }
                    emitEffect(AddGuideEffect.ShowSnackBar(result.errorMessage))
                }
                is Resource.Loading -> updateState { copy(isLoading = result.loading) }
            }
        }
    }


    private suspend fun uploadToImgBB(uri: Uri): String? {
        return try {
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes()
            inputStream?.close()

            if (bytes != null) {
                val requestBody = MultipartBody.Part.createFormData(
                    "image", "upload.jpg",
                    bytes.toRequestBody("image/*".toMediaTypeOrNull())
                )

                val result = uploadImageUseCase("YOUR_API_KEY", requestBody)
                if (result is Resource.Success) result.data else null
            } else null
        } catch (e: Exception) {
            null
        }
    }
}