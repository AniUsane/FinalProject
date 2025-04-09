package com.example.finalproject.presentation.ui.screen.registration

import androidx.lifecycle.viewModelScope
import com.example.finalproject.BaseViewModel
import com.example.finalproject.common.Resource
import com.example.finalproject.domain.usecase.RegisterUseCase
import com.example.finalproject.domain.usecase.ValidateEmailUseCase
import com.example.finalproject.domain.usecase.ValidatePasswordUseCase
import com.example.finalproject.presentation.mapper.toDomain
import com.example.finalproject.presentation.model.UserUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val emailValidator: ValidateEmailUseCase,
    private val passwordValidator: ValidatePasswordUseCase
) : BaseViewModel<RegistrationState, RegistrationEvent, RegistrationEffect>(RegistrationState()) {

    override fun obtainEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnEmailChanged -> updateState { copy(email = event.email) }
            is RegistrationEvent.OnPasswordChanged -> updateState { copy(password = event.password) }
            is RegistrationEvent.OnTogglePasswordVisibility -> updateState { copy(showPassword = !showPassword) }
            is RegistrationEvent.OnSubmit -> if(validateUser()) {registerUser()}
            is RegistrationEvent.ToggleEmailFieldsVisibility -> updateState { copy(showEmailFields = !showEmailFields) }
            is RegistrationEvent.OnFullNameChanged -> updateState { copy(fullName = event.fullName) }
            is RegistrationEvent.NavigateToLogin -> {
                viewModelScope.launch { emitEffect(RegistrationEffect.NavigateToLogin) }
            }
        }
    }

    private fun validateUser(): Boolean {
        val state = viewState.value
        val isEmailValid = emailValidator(state.email)
        val isPasswordValid = passwordValidator(state.password)

        updateState {
            copy(
                errorMessage = when {
                    !isEmailValid -> "Invalid email format"
                    !isPasswordValid -> "Password must be at least 6 characters"
                    else -> null
                }
            )
        }

        return isEmailValid && isPasswordValid
    }

    private fun registerUser() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }

            val userUi = UserUi(
                id = "",
                email = viewState.value.email,
                password = viewState.value.password,
                fullName = viewState.value.fullName)

            registerUseCase(userUi.toDomain()).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        updateState { copy(isLoading = false) }
                        emitEffect(RegistrationEffect.ShowSnackBar("Registration successful"))
                        emitEffect(RegistrationEffect.NavigateToLogin)
                    }
                    is Resource.Error -> updateState {
                        copy(isLoading = false, errorMessage = result.errorMessage)
                    }.also {
                        emitEffect(RegistrationEffect.ShowSnackBar(result.errorMessage))
                    }
                    is Resource.Loading -> updateState {
                        copy(isLoading = result.loading)
                    }
                }
            }
        }
    }
}