package com.example.finalproject.presentation.ui.screen.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.finalproject.BaseViewModel
import com.example.finalproject.BuildConfig
import com.example.finalproject.common.Resource
import com.example.finalproject.data.repository.dataStore.PreferenceKeys
import com.example.finalproject.domain.repository.auth.DataStoreRepository
import com.example.finalproject.domain.usecase.profile.DeleteProfileUseCase
import com.example.finalproject.domain.usecase.profile.DeleteUserUseCase
import com.example.finalproject.domain.usecase.profile.ProfileUseCase
import com.example.finalproject.domain.usecase.profile.UpdateProfileUseCase
import com.example.finalproject.domain.usecase.profile.UploadImageUseCase
import com.example.finalproject.presentation.mapper.toDomain
import com.example.finalproject.presentation.mapper.toPresentation
import com.example.finalproject.utils.ImageUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val profileUseCase: ProfileUseCase,
    private val updateProfile: UpdateProfileUseCase,
    private val deleteProfile: DeleteProfileUseCase,
    private val deleteUser: DeleteUserUseCase,
    private val dataStore: DataStoreRepository,
    private val uploadImageUseCase: UploadImageUseCase
): BaseViewModel<ProfileState, ProfileEvent, ProfileEffect>(ProfileState()) {
    override fun obtainEvent(event: ProfileEvent) {
        when(event){
            is ProfileEvent.LoadProfile -> loadProfile()
            is ProfileEvent.OnProfileImageSelected -> updateProfileImage(event.uri)
            is ProfileEvent.DeleteAccount -> deleteAccount()
            is ProfileEvent.LogoutClicked -> logout()
            is ProfileEvent.ChangeImageClicked -> {
                viewModelScope.launch { emitEffect(ProfileEffect.OpenImagePicker) }
            }
            is ProfileEvent.SettingsClicked -> {
                viewModelScope.launch { emitEffect(ProfileEffect.ShowSettingsDialog) }
            }
            is ProfileEvent.HelpClicked -> {
                viewModelScope.launch { emitEffect(ProfileEffect.ShowHelpDialog) }
            }
            is ProfileEvent.FeedbackClicked -> {
                viewModelScope.launch { emitEffect(ProfileEffect.ShowFeedbackDialog) }
            }

            else -> Unit
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            dataStore.readString(PreferenceKeys.USER_ID_KEY).collect { userId ->
                if (userId.isNotBlank()) {
                    profileUseCase(userId).collect { result ->
                        when (result) {
                            is Resource.Loading -> updateState { copy(isLoading = result.loading) }
                            is Resource.Success -> updateState {
                                copy(profile = result.data.toPresentation(), isLoading = false)
                            }
                            is Resource.Error -> updateState {
                                copy(errorMessage = result.errorMessage, isLoading = false)
                            }
                        }
                    }
                } else {
                    updateState { copy(errorMessage = "Missing userId in DataStore") }
                }
            }
        }
    }

    private fun logout(){
        viewModelScope.launch {
            dataStore.clear()
            emitEffect(ProfileEffect.NavigateToLogin)
        }
    }

    private fun deleteAccount(){
        val currentId = viewState.value.profile?.id ?: return

        viewModelScope.launch {
            deleteProfile(currentId).collect { profileResult ->
                when (profileResult) {
                    is Resource.Success -> {
                        deleteUser(currentId).collect { userResult ->
                            when (userResult) {
                                is Resource.Success -> {
                                    dataStore.clear()
                                    emitEffect(ProfileEffect.ShowSnackBar("Account successfully deleted"))
                                    emitEffect(ProfileEffect.NavigateToLogin)
                                }
                                is Resource.Error -> {
                                    emitEffect(ProfileEffect.ShowSnackBar("Profile deleted, but failed to delete user"))
                                }
                                else -> {}
                            }
                        }
                    }

                    is Resource.Error -> {
                        emitEffect(ProfileEffect.ShowSnackBar("Could not delete profile"))
                    }

                    else -> {}
                }
            }
        }
    }

    private fun updateProfileImage(newImageUrl: String){
        viewModelScope.launch {
            val uri = Uri.parse(newImageUrl)

            val imagePart = ImageUtils.prepareImage(uri, context)
            val result = uploadImageUseCase(BuildConfig.IMGBB_API_KEY, imagePart)

            when (result) {
                is Resource.Success -> {
                    val uploadedUrl = result.data
                    val currentProfile = viewState.value.profile ?: return@launch

                    val updatedProfile = currentProfile.copy(
                        profileImageUrl = uploadedUrl
                    )

                    updateProfile(updatedProfile.toDomain()).onEach { updateResult ->
                        when (updateResult) {
                            is Resource.Success -> {
                                updateState { copy(profile = updatedProfile) }
                                emitEffect(ProfileEffect.ShowSnackBar("Profile image updated"))
                            }
                            is Resource.Error -> {
                                emitEffect(ProfileEffect.ShowSnackBar("Failed to update profile"))
                            }
                            else -> Unit
                        }
                    }.launchIn(this)
                }

                is Resource.Error -> {
                    emitEffect(ProfileEffect.ShowSnackBar(result.errorMessage))
                }

                else -> Unit
            }
        }
    }
}