package com.example.finalproject.presentation.ui.screen.auth.login

import androidx.lifecycle.viewModelScope
import com.example.finalproject.BaseViewModel
import com.example.finalproject.common.Resource
import com.example.finalproject.data.repository.PreferenceKeys
import com.example.finalproject.domain.repository.DataStoreRepository
import com.example.finalproject.domain.usecase.auth.LoginUseCase
import com.example.finalproject.domain.usecase.auth.ValidateEmailUseCase
import com.example.finalproject.domain.usecase.auth.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val emailValidator: ValidateEmailUseCase,
    private val passwordValidator: ValidatePasswordUseCase,
    private val dataStoreManager: DataStoreRepository
) : BaseViewModel<LoginState, LoginEvent, LoginEffect>(LoginState()) {

    override fun obtainEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChanged -> updateState { copy(email = event.email) }
            is LoginEvent.OnPasswordChanged -> updateState { copy(password = event.password) }
            is LoginEvent.OnTogglePasswordVisibility -> updateState { copy(showPassword = !showPassword) }
            is LoginEvent.OnSubmit -> validateAndLogin(viewState.value.rememberMe)
            is LoginEvent.NavigateToRegister -> {
                viewModelScope.launch { emitEffect(LoginEffect.NavigateToRegister) }
            }
            is LoginEvent.OnToggleEmailVisibility -> updateState { copy(showEmailFields = !showEmailFields) }
            is LoginEvent.OnRememberMeChecked -> updateState { copy(rememberMe = event.checked) }
        }
    }

    private fun validateAndLogin(rememberMe: Boolean) {
        val current = viewState.value
        val isEmailValid = emailValidator(current.email)
        val isPasswordValid = passwordValidator(current.password)

        updateState {
            copy(
                emailError = if (!isEmailValid) "Invalid email format" else null,
                passwordError = if (!isPasswordValid) "Password must be at least 6 characters" else null,
                errorMessage = null
            )
        }

        if (!isEmailValid || !isPasswordValid) return

        login(current.email, current.password, rememberMe)
    }

    private fun login(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            updateState { copy(isLoading = true, errorMessage = null) }

            loginUseCase(email, password).collect { result ->
                when (result) {
                    is Resource.Loading -> updateState { copy(isLoading = result.loading) }

                    is Resource.Success -> {
                        updateState { copy(isLoading = false) }

                        result.data.userId?.let { dataStoreManager.saveString(PreferenceKeys.USER_ID_KEY, it) }
                        dataStoreManager.saveString(PreferenceKeys.PASSWORD_KEY, password)

                        if (rememberMe) {
                            with(dataStoreManager) {
                                launch {
                                    result.data.token?.let { saveString(PreferenceKeys.TOKEN_KEY, it) }
                                }
                            }
                        }

                        emitEffect(LoginEffect.NavigateToHome)
                    }

                    is Resource.Error -> updateState {
                        copy(isLoading = false, errorMessage = result.errorMessage)
                    }
                }
            }
        }
    }
}