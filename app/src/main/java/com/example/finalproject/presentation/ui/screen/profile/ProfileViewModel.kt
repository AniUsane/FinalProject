package com.example.finalproject.presentation.ui.screen.profile

import android.content.Context
import android.net.Uri
import android.util.Log.d
import androidx.lifecycle.viewModelScope
import com.example.finalproject.BaseViewModel
import com.example.finalproject.BuildConfig
import com.example.finalproject.common.Resource
import com.example.finalproject.data.repository.PreferenceKeys
import com.example.finalproject.domain.repository.DataStoreRepository
import com.example.finalproject.domain.usecase.DeleteProfileUseCase
import com.example.finalproject.domain.usecase.LogoutUseCase
import com.example.finalproject.domain.usecase.ProfileUseCase
import com.example.finalproject.domain.usecase.UpdateProfileUseCase
import com.example.finalproject.domain.usecase.UploadImageUseCase
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
    private val logout: LogoutUseCase,
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
            deleteProfile(currentId).onEach { result ->
                when(result) {
                    is Resource.Success -> {
                        emitEffect(ProfileEffect.ShowSnackBar("Profile successfully deleted"))
                        emitEffect(ProfileEffect.NavigateToLogin)
                    }
                    is Resource.Error -> {
                        emitEffect(ProfileEffect.ShowSnackBar("Could not delete profile"))
                    }
                    else -> {}
                }
            }.launchIn(this)
        }
    }

    private fun updateProfileImage(newImageUrl: String){
        viewModelScope.launch {
            // Convert String to Uri safely
            val uri = Uri.parse(newImageUrl)

            val imagePart = ImageUtils.prepareImage(uri, context)
            val result = uploadImageUseCase(BuildConfig.IMGBB_API_KEY, imagePart)

            when (result) {
                is Resource.Success -> {
                    val uploadedUrl = result.data
                    val currentProfile = viewState.value.profile ?: return@launch
                    val updated = currentProfile.copy(profileImageUrl = uploadedUrl)

                    updateProfile(updated.toDomain()).onEach { updateResult ->
                        when (updateResult) {
                            is Resource.Success -> {
                                updateState { copy(profile = updated) }
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




    //        val current = viewState.value.profile ?: return
//        val updated = current.copy(profileImageUrl = newImageUrl)
//
//        viewModelScope.launch {
//            updateProfile(updated.toDomain()).onEach { result ->
//                when (result) {
//                    is Resource.Success -> {
//                        updateState { copy(profile = updated) }
//                        emitEffect(ProfileEffect.ShowSnackBar("Profile image updated"))
//                    }
//
//                    is Resource.Error -> {
//                        emitEffect(ProfileEffect.ShowSnackBar("Failed to update profile"))
//                    }
//
//                    else -> {}
//                }
//            }.launchIn(this)
//        }
    }

}