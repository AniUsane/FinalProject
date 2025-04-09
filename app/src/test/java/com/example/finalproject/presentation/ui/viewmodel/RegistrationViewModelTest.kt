package com.example.finalproject.presentation.ui.viewmodel

import com.example.finalproject.common.Resource
import com.example.finalproject.domain.model.User
import com.example.finalproject.domain.usecase.RegisterUseCase
import com.example.finalproject.domain.usecase.ValidateEmailUseCase
import com.example.finalproject.domain.usecase.ValidatePasswordUseCase
import com.example.finalproject.presentation.ui.screen.registration.RegistrationEvent
import com.example.finalproject.presentation.ui.screen.registration.RegistrationViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegistrationViewModelTest {
    private lateinit var viewModel: RegistrationViewModel
    private val registerUseCase = mockk<RegisterUseCase>()
    private val emailValidator = mockk<ValidateEmailUseCase>()
    private val passwordValidator = mockk<ValidatePasswordUseCase>()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp(){
        Dispatchers.setMain(testDispatcher)

        viewModel = RegistrationViewModel(registerUseCase, emailValidator, passwordValidator)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `Invalid form shows email error`() = runTest {
        every { emailValidator("Wrongemail") } returns false
        every { passwordValidator("Test123") } returns true

        viewModel.obtainEvent(RegistrationEvent.OnEmailChanged("Wrongemail"))
        viewModel.obtainEvent(RegistrationEvent.OnPasswordChanged("Test123"))
        viewModel.obtainEvent(RegistrationEvent.OnSubmit)

        assertEquals("Invalid email format", viewModel.viewState.value.errorMessage)
    }

    @Test
    fun `Successful registration updates state and emits effects`() = runTest {
        every { emailValidator(any()) } returns true
        every { passwordValidator(any()) } returns true

        coEvery { registerUseCase(any()) } returns flow {
            emit(Resource.Success(User(
                id = "1",
                email = "test@gmail.com",
                password = "test123",
                fullName = "test test"
            )))
        }

        viewModel.obtainEvent(RegistrationEvent.OnEmailChanged("test@mail.com"))
        viewModel.obtainEvent(RegistrationEvent.OnPasswordChanged("password123"))
        viewModel.obtainEvent(RegistrationEvent.OnFullNameChanged("Ani"))
        viewModel.obtainEvent(RegistrationEvent.OnSubmit)

        advanceUntilIdle()
        assertFalse(viewModel.viewState.value.isLoading)
    }

    @Test
    fun `Registration error shows error message`() = runTest {
        every { emailValidator(any()) } returns true
        every { passwordValidator(any()) } returns true

        coEvery { registerUseCase(any()) } returns flow {
            emit(Resource.Error("Email already exists"))
        }

        viewModel.obtainEvent(RegistrationEvent.OnEmailChanged("test@mail.com"))
        viewModel.obtainEvent(RegistrationEvent.OnPasswordChanged("password123"))
        viewModel.obtainEvent(RegistrationEvent.OnFullNameChanged("Ani"))
        viewModel.obtainEvent(RegistrationEvent.OnSubmit)

        advanceUntilIdle()

        assertEquals("Email already exists", viewModel.viewState.value.errorMessage)
    }

}