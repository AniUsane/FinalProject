package com.example.finalproject.presentation.ui.screen.bookHotel.citySearch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.finalproject.domain.model.City
import com.example.finalproject.presentation.ui.screen.components.CollectEffect
import com.example.finalproject.presentation.ui.screen.components.Dimensions.mediumSpace
import com.example.finalproject.presentation.ui.screen.components.StyledTextField
import com.example.finalproject.presentation.ui.theme.Black

@Composable
fun CitySearchScreen(
    viewModel: CitySearchViewModel = hiltViewModel(),
    onCitySelected: (City) -> Unit,
    onBackPressed: () -> Unit
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    CollectEffect(flow = viewModel.effects) { effect ->
        when (effect) {
            is CitySearchEffect.ShowSnackbar -> {
                snackBarHostState.showSnackbar(effect.message)
            }
            is CitySearchEffect.NavigateBackWithResult -> {
                onCitySelected(effect.city)
            }
        }
    }

    CitySearchContent(
        state = state,
        onQueryChanged = { viewModel.obtainEvent(CitySearchEvent.OnQueryChanged(it)) },
        onCitySelected = { viewModel.obtainEvent(CitySearchEvent.OnCitySelected(it)) },
        snackBarHostState = snackBarHostState
    )
}

@Composable
fun CitySearchContent(
    state: CitySearchState,
    onQueryChanged: (String) -> Unit,
    onCitySelected: (City) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier.fillMaxWidth()){
            Text("Search destinations",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = Black
            )
        }
        Spacer(modifier = Modifier.padding(mediumSpace))

        Column(modifier = Modifier.padding(mediumSpace)) {
            StyledTextField(
                value = state.query,
                onValueChange = { onQueryChanged(it) },
                label = "Search destination...",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            LazyColumn {
                items(state.cityList) { city ->
                    Text(
                        text = "${city.name}, ${city.countryName}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCitySelected(city) }
                            .padding(16.dp)
                    )
                }
            }
        }

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CitySearchContentPreview() {
    CitySearchContent(
        state = CitySearchState(
            query = "Par",
            cityList = listOf(
                City(name = "Paris", countryName = "France", iataCode = "PAR"),
                City(name = "Paris, Texas", countryName = "USA", iataCode = "PRX")
            ),
            isLoading = false,
            errorMessage = null
        ),
        onQueryChanged = {},
        onCitySelected = {},
        snackBarHostState = remember { SnackbarHostState() }
    )
}