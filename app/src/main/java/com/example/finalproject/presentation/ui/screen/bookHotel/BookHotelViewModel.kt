package com.example.finalproject.presentation.ui.screen.bookHotel

import androidx.lifecycle.viewModelScope
import com.example.finalproject.BaseViewModel
import com.example.finalproject.domain.usecase.HotelSearchUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookHotelViewModel @Inject constructor(
    private val hotelSearchUseCase: HotelSearchUseCase
): BaseViewModel<BookHotelState, BookHotelEvent, BookHotelEffect>(BookHotelState()) {
    override fun obtainEvent(event: BookHotelEvent) {
        when(event){
            is BookHotelEvent.OnDateChanged -> updateState {
                copy(checkIn = event.checkIn, checkOut = event.checkOut)
            }
            is BookHotelEvent.OnDestinationChanged -> updateState { copy(destination = event.destination) }
            is BookHotelEvent.OnTravelersChanged -> updateState { copy(travelers = event.travelers) }
            is BookHotelEvent.OnSearchClicked -> {
                viewModelScope.launch {
                    onSearch()
                }
            }
        }
    }

    private suspend fun onSearch(){
        val state = viewState.value
        if(state.destination.isBlank()){
            emitEffect(BookHotelEffect.ShowSnackBar("Please enter destination"))
            return
        }

        val url = hotelSearchUseCase(
            destination = state.destination,
            checkIn = state.checkIn,
            checkOut = state.checkOut,
            travelers = state.travelers
        )

        emitEffect(BookHotelEffect.NavigateToSearchResults(url))
    }
}


//CollectEffect(flow = viewModel.effects) { effect ->
//    when (effect) {
//        is BookHotelEffect.ShowSnackBar -> snackbarHostState.showSnackbar(effect.message)
//        is BookHotelEffect.NavigateToSearchResults -> {
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(effect.url))
//            context.startActivity(intent)
//        }
//        is BookHotelEffect.OpenDatePicker -> { /* Open calendar */ }
//        is BookHotelEffect.OpenTravelerBottomSheet -> { /* Open sheet */ }
//    }
//}