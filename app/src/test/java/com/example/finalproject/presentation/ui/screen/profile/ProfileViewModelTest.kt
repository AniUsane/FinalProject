package com.example.finalproject.presentation.ui.screen.profile

import android.content.Context
import app.cash.turbine.test
import com.example.finalproject.common.Resource
import com.example.finalproject.data.repository.dataStore.PreferenceKeys
import com.example.finalproject.domain.model.profile.Profile
import com.example.finalproject.domain.repository.auth.DataStoreRepository
import com.example.finalproject.domain.usecase.profile.DeleteProfileUseCase
import com.example.finalproject.domain.usecase.profile.DeleteUserUseCase
import com.example.finalproject.domain.usecase.profile.ProfileUseCase
import com.example.finalproject.domain.usecase.profile.UpdateProfileUseCase
import com.example.finalproject.domain.usecase.profile.UploadImageUseCase
import com.example.finalproject.presentation.mapper.toPresentation
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {
    private val profileUseCase = mockk<ProfileUseCase>()
    private val updateProfileUseCase = mockk<UpdateProfileUseCase>()
    private val deleteProfileUseCase = mockk<DeleteProfileUseCase>()
    private val deleteUserUseCase = mockk<DeleteUserUseCase>()
    private val uploadImageUseCase = mockk<UploadImageUseCase>()
    private val dataStore = mockk<DataStoreRepository>(relaxed = true)
    private val context = mockk<Context>()

    private lateinit var viewModel: ProfileViewModel

    @BeforeEach
    fun setup(){
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = ProfileViewModel(
            context,
            profileUseCase,
            updateProfileUseCase,
            deleteProfileUseCase,
            deleteUserUseCase,
            dataStore,
            uploadImageUseCase
        )
    }

    @AfterEach
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `load profile successfully updates state`() = runTest {
        every { dataStore.readString(PreferenceKeys.USER_ID_KEY) } returns flowOf("123")
        coEvery { profileUseCase("123") } returns flowOf(Resource.Success(FakeProfile.domain()))
        viewModel.obtainEvent(ProfileEvent.LoadProfile)
        testScheduler.advanceUntilIdle()
        assertThat(viewModel.viewState.value.profile).isEqualTo(FakeProfile.presentation())
    }

    @Test
    fun `loading profile shows error message`() = runTest {
        every{dataStore.readString(PreferenceKeys.USER_ID_KEY)} returns flowOf("123")
        coEvery { profileUseCase("123") } returns flowOf(Resource.Error("error"))
        viewModel.obtainEvent(ProfileEvent.LoadProfile)
        testScheduler.advanceUntilIdle()
        assertThat(viewModel.viewState.value.errorMessage).isEqualTo("error")
    }

    @Test
    fun `load profile is missing userId`() = runTest {
        every{dataStore.readString(PreferenceKeys.USER_ID_KEY)} returns flowOf("")
        viewModel.obtainEvent(ProfileEvent.LoadProfile)
        testScheduler.advanceUntilIdle()
        assertThat(viewModel.viewState.value.errorMessage).contains("Missing userId in DataStore")
    }

    @Test
    fun `logout clears Datastore and emits navigation to login`() = runTest {
        viewModel.obtainEvent(ProfileEvent.LogoutClicked)

        viewModel.effects.test {
            assertEquals(ProfileEffect.NavigateToLogin, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `deleteAccount emits snackBar and navigation when successful`() = runTest {
        val userId = "123"
        every { dataStore.readString(PreferenceKeys.USER_ID_KEY) } returns flowOf(userId)
        coEvery { profileUseCase(userId) } returns flowOf(Resource.Success(FakeProfile.domain()))
        coEvery { deleteProfileUseCase(userId) } returns flowOf(Resource.Success(Unit))
        coEvery { deleteUserUseCase(userId) } returns flowOf(Resource.Success(Unit))

        val collected = mutableListOf<ProfileEffect>()
        val job = launch {
            viewModel.effects.collect {
                collected.add(it)
            }
        }

        viewModel.obtainEvent(ProfileEvent.LoadProfile)
        advanceUntilIdle()
        viewModel.obtainEvent(ProfileEvent.DeleteAccount)
        advanceUntilIdle()

        assertThat(collected).containsExactly(
            ProfileEffect.ShowSnackBar("Account successfully deleted"),
            ProfileEffect.NavigateToLogin
        )
        job.cancel()
    }

    @Test
    fun `ChangeImageClicked emits OpenImagePicker`() = runTest {
        viewModel.effects.test {
            viewModel.obtainEvent(ProfileEvent.ChangeImageClicked)
            assertEquals(ProfileEffect.OpenImagePicker, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `SettingsClicked emits ShowSettingsDialog`() = runTest {
        viewModel.effects.test {
            viewModel.obtainEvent(ProfileEvent.SettingsClicked)
            assertEquals(ProfileEffect.ShowSettingsDialog, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `HelpClicked emits ShowHelpDialog`() = runTest {
        viewModel.effects.test {
            viewModel.obtainEvent(ProfileEvent.HelpClicked)
            assertEquals(ProfileEffect.ShowHelpDialog, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `FeedbackClicked emits ShowFeedbackDialog`() = runTest {
        viewModel.effects.test {
            viewModel.obtainEvent(ProfileEvent.FeedbackClicked)
            assertEquals(ProfileEffect.ShowFeedbackDialog, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

}


object FakeProfile {
    fun domain() = Profile(
        id = "123", userId = "u1", username = "test",
        fullName = "Test", profileImageUrl = "old_url",
        bio = null, guides = emptyList(), trips = emptyList()
    )

    fun presentation() = domain().toPresentation()
}