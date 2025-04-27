package com.example.finalproject.presentation.ui.screen.bookHotel

import android.util.Log.d
import androidx.lifecycle.viewModelScope
import com.example.finalproject.BaseViewModel
import com.example.finalproject.common.Resource
import com.example.finalproject.data.repository.dataStore.PreferenceKeys
import com.example.finalproject.domain.repository.auth.DataStoreRepository
import com.example.finalproject.domain.usecase.bookHotel.AddRecentSearchUseCase
import com.example.finalproject.domain.usecase.bookHotel.GetRecentSearchesUseCase
import com.example.finalproject.domain.usecase.bookHotel.HotelSearchUseCase
import com.example.finalproject.presentation.mapper.toDomain
import com.example.finalproject.presentation.model.bookHotel.RecentSearchUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class BookHotelViewModel @Inject constructor(
    private val hotelSearchUseCase: HotelSearchUseCase,
    private val addRecentSearchUseCase: AddRecentSearchUseCase,
    private val getRecentSearchesUseCase: GetRecentSearchesUseCase,
    private val dataStoreRepository: DataStoreRepository
): BaseViewModel<BookHotelState, BookHotelEvent, BookHotelEffect>(BookHotelState()) {
    private var userId: String? = null

    override fun obtainEvent(event: BookHotelEvent) {
        when (event) {
            is BookHotelEvent.OnDateChanged -> updateState {
                copy(checkIn = event.checkIn, checkOut = event.checkOut)
            }
            is BookHotelEvent.OnDestinationChanged -> updateState { copy(destination = event.destination) }
            is BookHotelEvent.OnTravelersChanged -> updateState { copy(travelers = event.travelers) }
            is BookHotelEvent.OnSearchClicked -> {
                viewModelScope.launch {
                    saveRecentSearch(destination = viewState.value.destination)
                    onSearch()
                }
            }
            is BookHotelEvent.OnDestinationFieldClicked -> viewModelScope.launch {
                emitEffect(BookHotelEffect.OpenCitySearchScreen)
            }
            is BookHotelEvent.OnDateFieldClicked -> viewModelScope.launch {
                emitEffect(BookHotelEffect.OpenDatePicker)
            }
            is BookHotelEvent.OnTravelersFieldClicked -> viewModelScope.launch {
                emitEffect(BookHotelEffect.OpenTravelerBottomSheet)
            }
        }
    }

    private fun saveRecentSearch(destination: String) {
        viewModelScope.launch {
            userId?.let { id ->
                if (destination.isNotBlank()) {
                    val state = viewState.value
                    val recentSearchUi = RecentSearchUi(
                        searchQuery = destination,
                        checkInDate = state.checkIn.format(DateTimeFormatter.ofPattern("MMM d")),
                        checkOutDate = state.checkOut.format(DateTimeFormatter.ofPattern("MMM d")),
                        guests = state.travelers.adults,
                        rooms = state.travelers.rooms
                    )
                    val recentSearch = recentSearchUi.toDomain(id)
                    addRecentSearchUseCase(recentSearch).collect { resource ->
                        when (resource) {
                            is Resource.Success -> loadRecentSearches()
                            is Resource.Error -> { updateState { copy(isLoading = false) }
                                emitEffect(BookHotelEffect.ShowSnackBar(resource.errorMessage)) }
                            is Resource.Loading -> { updateState { copy(isLoading = true) } }
                        }
                    }
                }
            }
        }
    }


    fun initializeUser() {
        viewModelScope.launch {
            userId = dataStoreRepository.readString(PreferenceKeys.USER_ID_KEY).first()

            if (!userId.isNullOrBlank()) {
                loadRecentSearches()
            } else {
                updateState {
                    copy(
                        recentSearches = emptyList(),
                        isLoading = false,
                        errorMessage = ""
                    )
                }
            }
        }
    }

    private fun loadRecentSearches() {
        viewModelScope.launch {
            userId?.let { id ->
                d("BookHotelViewModel", "Loaded userId = $userId")
                getRecentSearchesUseCase(id).collect { resource ->
                    when (resource) {
                        is Resource.Success -> updateState {
                            copy(
                                recentSearches = resource.data.map { search ->
                                    RecentSearchUi(
                                        searchQuery = search.searchQuery,
                                        checkInDate = search.checkInDate,
                                        checkOutDate = search.checkOutDate,
                                        guests = search.guests,
                                        rooms = search.rooms
                                    )
                                }
                            )
                        }
                        is Resource.Error -> updateState {
                            copy(
                                isLoading = false,
                                errorMessage = resource.errorMessage
                            )
                        }
                        is Resource.Loading -> updateState {
                            copy(
                                isLoading = true,
                                errorMessage = ""
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun onSearch() {
        val state = viewState.value
        when {
            state.destination.isBlank() -> {
                emitEffect(BookHotelEffect.ShowSnackBar("Please select a destination"))
            }
            else -> {
                val url = hotelSearchUseCase(
                    destination = state.destination,
                    checkIn = state.checkIn,
                    checkOut = state.checkOut,
                    travelers = state.travelers
                )
                emitEffect(BookHotelEffect.NavigateToSearchResults(url))
            }
        }
    }
}