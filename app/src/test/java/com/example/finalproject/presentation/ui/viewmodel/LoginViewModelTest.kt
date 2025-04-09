package com.example.finalproject.presentation.ui.viewmodel

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.LoginResponse
import com.example.finalproject.domain.model.PreferenceKeys
import com.example.finalproject.domain.repository.DataStoreRepository
import com.example.finalproject.domain.usecase.LoginUseCase
import com.example.finalproject.domain.usecase.ValidateEmailUseCase
import com.example.finalproject.domain.usecase.ValidatePasswordUseCase
import com.example.finalproject.presentation.ui.screen.login.LoginEvent
import com.example.finalproject.presentation.ui.screen.login.LoginViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest{
    private lateinit var viewModel: LoginViewModel
    private val loginUseCase = mockk<LoginUseCase>()
    private val emailValidator = mockk<ValidateEmailUseCase>()
    private val passwordValidator = mockk<ValidatePasswordUseCase>()
    private val dataStoreRepository = mockk<DataStoreRepository>()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp(){
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(loginUseCase, emailValidator, passwordValidator, dataStoreRepository)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `Valid email and password triggers login and stores token`() = runTest {
        every { emailValidator("test@gmail.com") } returns true
        every { passwordValidator("password123") } returns true

        val loginResponse = LoginResponse(
            id = "1",
            status = "success",
            message = "Login success",
            token = "abc123",
            userId = "1"
        )

        coEvery { loginUseCase(any(), any()) } returns flowOf(Resource.Success(loginResponse))
        coEvery { dataStoreRepository.saveString(any(), any()) } just Runs

        viewModel.obtainEvent(LoginEvent.OnEmailChanged("test@gmail.com"))
        viewModel.obtainEvent(LoginEvent.OnPasswordChanged("password123"))
        viewModel.obtainEvent(LoginEvent.OnRememberMeChecked(true))
        viewModel.obtainEvent(LoginEvent.OnSubmit)

        advanceUntilIdle()

        coVerify {
            dataStoreRepository.saveString(PreferenceKeys.TOKEN_KEY, "abc123")
            dataStoreRepository.saveString(PreferenceKeys.USER_ID_KEY, "1")
        }
    }

    @Test
    fun `Invalid email does not login`() = runTest {
        every { emailValidator("invalid") } returns false
        every { passwordValidator("pass123") } returns true

        viewModel.obtainEvent(LoginEvent.OnEmailChanged("invalid"))
        viewModel.obtainEvent(LoginEvent.OnPasswordChanged("pass123"))
        viewModel.obtainEvent(LoginEvent.OnSubmit)

        advanceUntilIdle()

        coVerify(exactly = 0) { loginUseCase(any(), any()) }
    }

    @Test
    fun `Login failure shows error message`() = runTest {
        every { emailValidator("test@gmail.com") } returns true
        every { passwordValidator("pass123") } returns true

        coEvery { loginUseCase(any(), any()) } returns flow {
            emit(Resource.Error("Login failed"))
        }

        viewModel.obtainEvent(LoginEvent.OnEmailChanged("test@gmail.com"))
        viewModel.obtainEvent(LoginEvent.OnPasswordChanged("pass123"))
        viewModel.obtainEvent(LoginEvent.OnSubmit)

        advanceUntilIdle()

        assert(viewModel.viewState.value.errorMessage == "Login failed")
    }

}