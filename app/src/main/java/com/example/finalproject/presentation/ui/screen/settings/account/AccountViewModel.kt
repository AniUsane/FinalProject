package com.example.finalproject.presentation.ui.screen.settings.account

import android.util.Log.d
import androidx.lifecycle.viewModelScope
import com.example.finalproject.BaseViewModel
import com.example.finalproject.common.Resource
import com.example.finalproject.data.repository.PreferenceKeys
import com.example.finalproject.domain.model.Profile
import com.example.finalproject.domain.model.User
import com.example.finalproject.domain.repository.DataStoreRepository
import com.example.finalproject.domain.repository.ProfileRepository
import com.example.finalproject.domain.repository.UserRepository
import com.example.finalproject.domain.usecase.DeleteProfileUseCase
import com.example.finalproject.domain.usecase.DeleteUserUseCase
import com.example.finalproject.domain.usecase.UpdateProfileUseCase
import com.example.finalproject.domain.usecase.UpdateUserUseCase
import com.example.finalproject.domain.usecase.auth.ValidateEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val deleteProfileUseCase: DeleteProfileUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val dataStore: DataStoreRepository,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository
): BaseViewModel<AccountState, AccountEvent, AccountEffect>(AccountState()) {
    override fun obtainEvent(event: AccountEvent) {
        when (event) {
            is AccountEvent.NameChanged -> updateState { copy(name = event.name) }
            is AccountEvent.EmailChanged -> updateState { copy(email = event.email) }
            is AccountEvent.UsernameChanged -> updateState { copy(username = event.username) }
            is AccountEvent.SaveClicked -> updateAccount()
            is AccountEvent.DeleteClicked -> deleteAccount()
            is AccountEvent.LoadAccount -> loadAccountInfo()
        }
    }

    fun loadAccountInfo() {
        viewModelScope.launch {
            val userId = dataStore.readString(PreferenceKeys.USER_ID_KEY).firstOrNull()
            if (userId.isNullOrBlank()) {
                emitEffect(AccountEffect.ShowSnackBar("Missing user ID"))
                return@launch
            }

            val userDeferred = async { userRepository.getUserById(userId) }
            val profileDeferred = async { profileRepository.getAccProfileUserId(userId) }

            val user = userDeferred.await()
            val profile = profileDeferred.await()

            if (user != null && profile != null) {
                updateState {
                    copy(
                        id = user.id,
                        name = user.fullName ?: "",
                        email = user.email,
                        fullName = user.fullName ?: profile.fullName,
                        username = profile.username,
                        profileImageUrl = profile.profileImageUrl ?: "",
                        user = user,
                        profile = profile
                    )
                }
            } else {
                emitEffect(AccountEffect.ShowSnackBar("Failed to load account info"))
            }
        }
    }

    private fun deleteAccount() {
        val id = viewState.value.id
        if (id.isBlank()) return

        viewModelScope.launch {
            deleteProfileUseCase(id).collect { profileResult ->
                when (profileResult) {
                    is Resource.Success -> {
                        deleteUserUseCase(id).collect { userResult ->
                            when (userResult) {
                                is Resource.Success -> {
                                    dataStore.clear()
                                    emitEffect(AccountEffect.ShowSnackBar("Account successfully deleted"))
                                    emitEffect(AccountEffect.NavigateToLogin)
                                }

                                is Resource.Error -> {
                                    emitEffect(AccountEffect.ShowSnackBar("Profile deleted, but user deletion failed"))
                                }

                                else -> {}
                            }
                        }
                    }

                    is Resource.Error -> {
                        emitEffect(AccountEffect.ShowSnackBar("Could not delete profile"))
                    }

                    else -> {}
                }
            }
        }
    }

    private fun updateAccount() {
        viewModelScope.launch {
            val state = viewState.value

            val userState = state.user
            val profileState = state.profile

            val id = dataStore.readString(PreferenceKeys.USER_ID_KEY).firstOrNull()
            val fullName = state.name
            val email = state.email
            val username = state.username
            val password = dataStore.readString(PreferenceKeys.PASSWORD_KEY).firstOrNull() ?: ""

            if (email.isNotBlank() && !validateEmailUseCase(email)) {
                updateState { copy(emailError = "Invalid email format") }
                return@launch
            } else {
                updateState { copy(emailError = null) }
            }

            val shouldUpdateUser = fullName != userState?.fullName || email != userState.email
            val shouldUpdateProfile = username != profileState?.username || fullName != profileState.fullName

            if (!shouldUpdateUser && !shouldUpdateProfile) {
                emitEffect(AccountEffect.ShowSnackBar("Nothing to update"))
                return@launch
            }

            if (shouldUpdateUser) {
                d("userError", " shouldUpdateUser = true — preparing to send update")

                if (id.isNullOrBlank()) {
                    d("userError", " ID is null! Cannot proceed.")
                    emitEffect(AccountEffect.ShowSnackBar("User ID is missing."))
                    return@launch
                }

                val user = User(
                    id = id,
                    fullName = fullName,
                    email = email,
                    password = password
                )

                d("userError", " calling updateUserUseCase with $user")

                launch {
                    d("userError", " calling updateUserUseCase...")
                    updateUserUseCase(user).collect { result ->
                        when (result) {
                            is Resource.Success -> emitEffect(AccountEffect.ShowSnackBar("User updated!"))
                            is Resource.Error -> emitEffect(AccountEffect.ShowSnackBar("User update failed: ${result.errorMessage}"))
                            else -> {}
                        }
                    }
                }
            }

            if (shouldUpdateProfile) {
                val profile = Profile(
                    id = id ?: return@launch,
                    userId = profileState?.userId ?: return@launch,
                    username = username,
                    fullName = fullName,
                    profileImageUrl = state.profileImageUrl,
                    bio = profileState.bio ?: "",
                    trips = profileState.trips ,
                    guides = profileState.guides
                )

                launch {
                    updateProfileUseCase(profile).collect { result ->
                        when (result) {
                            is Resource.Success -> emitEffect(AccountEffect.ShowSnackBar("Username updated!"))
                            is Resource.Error -> emitEffect(AccountEffect.ShowSnackBar("Username update failed: ${result.errorMessage}"))
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}


