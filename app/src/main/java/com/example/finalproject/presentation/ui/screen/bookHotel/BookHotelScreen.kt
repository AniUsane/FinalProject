package com.example.finalproject.presentation.ui.screen.bookHotel

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.finalproject.presentation.ui.screen.components.CollectEffect
import com.example.finalproject.presentation.ui.screen.components.Dimensions.bigSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.mediumSpace
import com.example.finalproject.presentation.ui.screen.components.StyledButton
import com.example.finalproject.presentation.ui.screen.components.StyledTextField
import com.example.finalproject.presentation.ui.theme.Black
import com.example.finalproject.presentation.ui.theme.White
import java.time.LocalDate

@Composable
fun BookHotelScreen(
    viewModel: BookHotelViewModel = hiltViewModel()
){
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackBookHotelState = remember{SnackbarHostState()}

    CollectEffect(flow = viewModel.effects) { effect ->
        when(effect) {
            is BookHotelEffect.ShowSnackBar -> snackBookHotelState.showSnackbar(effect.message)
            is BookHotelEffect.NavigateToSearchResults -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(effect.url))
                context.startActivity(intent)
            }
            is BookHotelEffect.OpenDatePicker -> {}
            is BookHotelEffect.OpenTravelerBottomSheet -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        BookHotelContent(
            state = state,
            onEvent = viewModel::obtainEvent
        )
//        SnackbarHost(
//            hostState = snackBarHostState,
//            modifier = Modifier.align(Alignment.BottomCenter)
//        )
    }
}

@Composable
fun BookHotelContent(
    state: BookHotelState,
    onEvent: (BookHotelEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.padding(top = bigSpace))
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = mediumSpace),
            horizontalArrangement = Arrangement.Center){
            Text(
                text = "Book hotels",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = Black)
        }

        StyledTextField(
            value = state.destination,
            onValueChange = { onEvent(BookHotelEvent.OnDestinationChanged(it)) },
            label = "Destination",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onEvent(BookHotelEvent.OnDateChanged(state.checkIn, state.checkOut)) },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Check-in: ${state.checkIn}")
            Text(text = "Check-out: ${state.checkOut}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onEvent(BookHotelEvent.OnTravelersChanged(state.travelers)) },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Rooms: ${state.travelers.rooms}, Adults: ${state.travelers.adults}, Children: ${state.travelers.children}")
        }

        Spacer(modifier = Modifier.height(32.dp))

        StyledButton(
            text = "Search Hotels",
            onClick = { onEvent(BookHotelEvent.OnSearchClicked) }
        )
    }
}

@Composable
@Preview
fun BookHotelContentPreview(){
    BookHotelContent(
        state = BookHotelState(
            destination = "Paris",
            checkIn = LocalDate.now(),
            checkOut = LocalDate.now().plusDays(5),
            travelers = Travelers(rooms = 1, adults = 2, children = 1),
            isLoading = false,
            errorMessage = "",
            recentSearches = listOf("Rome", "Tokyo", "Barcelona")
        ),
        onEvent = {}
    )
}