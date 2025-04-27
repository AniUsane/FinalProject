package com.example.finalproject.presentation.ui.screen.bookHotel.calendar

import androidx.lifecycle.viewModelScope
import com.example.finalproject.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseDateViewModel @Inject constructor() : BaseViewModel<ChooseDateState, ChooseDateEvent, ChooseDateEffect>(ChooseDateState()) {

    override fun obtainEvent(event: ChooseDateEvent) {
        when (event) {
            is ChooseDateEvent.StartDateSelected -> updateState { copy(startDate = event.date) }
            is ChooseDateEvent.EndDateSelected -> updateState { copy(endDate = event.date) }
            is ChooseDateEvent.DoneClicked -> {
                val start = viewState.value.startDate
                val end = viewState.value.endDate
                if (start != null && end != null) {
                    viewModelScope.launch {
                        emitEffect(ChooseDateEffect.Done(start to end))
                    }
                }
            }
            is ChooseDateEvent.CancelClicked -> {
                viewModelScope.launch {
                    emitEffect(ChooseDateEffect.Cancel)
                }
            }
        }
    }
}
