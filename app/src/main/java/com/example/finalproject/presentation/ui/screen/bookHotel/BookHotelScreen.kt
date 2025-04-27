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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.finalproject.R
import com.example.finalproject.presentation.model.bookHotel.RecentSearchUi
import com.example.finalproject.presentation.model.bookHotel.Travelers
import com.example.finalproject.presentation.ui.screen.components.CollectEffect
import com.example.finalproject.presentation.ui.screen.components.Dimensions.bigSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.mediumSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.smallSpace
import com.example.finalproject.presentation.ui.theme.Black
import com.example.finalproject.presentation.ui.theme.Gray
import com.example.finalproject.presentation.ui.theme.OrangeColor
import com.example.finalproject.presentation.ui.theme.White
import java.time.LocalDate


@Composable
fun BookHotelScreen(
    viewModel: BookHotelViewModel = hiltViewModel(),
    navigateToCitySearch: () -> Unit,
    navigateToChooseDate: () -> Unit,
    navigateToTravelers: () -> Unit,
    savedStateHandle: SavedStateHandle
){
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackBookHotelState = remember{SnackbarHostState()}

    val cityNameFlow = savedStateHandle.getStateFlow<String?>("city_name", null)
    val cityName by cityNameFlow.collectAsStateWithLifecycle()

    val checkInFlow = savedStateHandle.getStateFlow<LocalDate?>("check_in", null)
    val checkOutFlow = savedStateHandle.getStateFlow<LocalDate?>("check_out", null)

    val checkIn by checkInFlow.collectAsStateWithLifecycle()
    val checkOut by checkOutFlow.collectAsStateWithLifecycle()

    val travelersFlow = savedStateHandle.getStateFlow<Travelers?>("travelers", null)
    val travelers by travelersFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initializeUser()
    }

    LaunchedEffect(travelers) {
        travelers?.let {
            viewModel.obtainEvent(BookHotelEvent.OnTravelersChanged(it))
            savedStateHandle["travelers"] = null
        }
    }

    LaunchedEffect(checkIn, checkOut) {
        if (checkIn != null && checkOut != null) {
            viewModel.obtainEvent(BookHotelEvent.OnDateChanged(checkIn!!, checkOut!!))

            savedStateHandle["check_in"] = null
            savedStateHandle["check_out"] = null
        }
    }

    LaunchedEffect(cityName) {
        cityName?.let { city ->
            viewModel.obtainEvent(BookHotelEvent.OnDestinationChanged(cityName!!))
            savedStateHandle["selected_city"] = null
        }
    }

    CollectEffect(flow = viewModel.effects) { effect ->
        when(effect) {
            is BookHotelEffect.ShowSnackBar -> snackBookHotelState.showSnackbar(effect.message)
            is BookHotelEffect.NavigateToSearchResults -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(effect.url))
                context.startActivity(intent)
            }
            is BookHotelEffect.OpenDatePicker -> { navigateToChooseDate() }
            is BookHotelEffect.OpenTravelerBottomSheet -> { navigateToTravelers()}
            is BookHotelEffect.OpenCitySearchScreen -> navigateToCitySearch()
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        BookHotelContent(
            state = state,
            onEvent = viewModel::obtainEvent
        )

        SnackbarHost(
            hostState = snackBookHotelState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
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
            .background(White)
            .padding(horizontal = mediumSpace),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(bigSpace))

        Text(
            text = stringResource(R.string.book_hotels),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium)
                .clickable { onEvent(BookHotelEvent.OnDestinationFieldClicked) }
                .padding(mediumSpace)
        ) {
            if (state.destination.isBlank()) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Gray)) {
                            append(stringResource(R.string.where))
                        }
                        withStyle(style = SpanStyle(color = Gray)) {
                            append(stringResource(R.string.city_you_re_visiting))
                        }
                    }
                )
            } else {
                Text(
                    text = state.destination,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Black
                )
            }
        }

        Spacer(modifier = Modifier.height(smallSpace))

        Spacer(modifier = Modifier.height(mediumSpace))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(smallSpace)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        MaterialTheme.shapes.medium
                    )
                    .clickable { onEvent(BookHotelEvent.OnDateFieldClicked) }
                    .padding(smallSpace)
            ) {
                Text(
                    text = stringResource(R.string.when_string),
                    style = MaterialTheme.typography.labelSmall,
                    color = Gray
                )
                Spacer(modifier = Modifier.height(smallSpace))
                Text(
                    text = "${state.checkIn.monthValue}/${state.checkIn.dayOfMonth} - ${state.checkOut.monthValue}/${state.checkOut.dayOfMonth}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Black
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        MaterialTheme.shapes.medium
                    )
                    .clickable { onEvent(BookHotelEvent.OnTravelersFieldClicked) }
                    .padding(smallSpace)
            ) {
                Text(
                    text = stringResource(R.string.travelers),
                    style = MaterialTheme.typography.labelSmall,
                    color = Gray
                )
                Spacer(modifier = Modifier.height(smallSpace))
                Text(
                    text = "${state.travelers.adults} adults • ${state.travelers.children} children",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Black
                )
            }
        }

        Spacer(modifier = Modifier.height(mediumSpace))

        Button(
            onClick = { onEvent(BookHotelEvent.OnSearchClicked) },
            modifier = Modifier.fillMaxWidth(0.5f),
            colors = ButtonDefaults.buttonColors(containerColor = OrangeColor)
        ) {
            Text(
                text = stringResource(R.string.search),
                color = White,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(mediumSpace))

        if (state.recentSearches.isNotEmpty()) {
            Text(
                text = stringResource(R.string.recently_searched),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Black,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(smallSpace))

            Column(
                verticalArrangement = Arrangement.spacedBy(smallSpace)
            ) {
                state.recentSearches.asReversed().forEach { search ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = smallSpace)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Hotel,
                            contentDescription = null,
                            modifier = Modifier.size(mediumSpace)
                        )
                        Spacer(modifier = Modifier.width(smallSpace))
                        Column {
                            Text(
                                text = search.searchQuery,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                color = Black
                            )
                            Text(
                                text = "${search.checkInDate} — ${search.checkOutDate} • ${search.guests} guests • ${search.rooms} room",
                                style = MaterialTheme.typography.bodySmall,
                                color = Gray
                            )
                        }
                    }
                }
            }
        }
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
            recentSearches = listOf(
                RecentSearchUi(searchQuery = "Tbilisi",
                    checkInDate = "May 1",
                    checkOutDate = "May 5",
                    guests = 3,
                    rooms = 2)
            )
        ),
        onEvent = {}
    )
}