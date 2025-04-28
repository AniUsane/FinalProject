package com.example.finalproject.presentation.ui.screen.bookHotel.citySearch

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.finalproject.R
import com.example.finalproject.domain.model.bookHotel.City
import com.example.finalproject.presentation.ui.screen.components.CollectEffect
import com.example.finalproject.presentation.ui.screen.components.Dimensions.bigSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.mediumSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.smallSpace

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
            is CitySearchEffect.ShowSnackBar -> {
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
        snackBarHostState = snackBarHostState,
        navigateBack =  onBackPressed
    )
}

@Composable
fun CitySearchContent(
    state: CitySearchState,
    onQueryChanged: (String) -> Unit,
    onCitySelected: (City) -> Unit,
    snackBarHostState: SnackbarHostState,
    navigateBack: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val paddingValue = if(isLandscape) bigSpace else mediumSpace

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = paddingValue)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(bigSpace))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = navigateBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.back_arrow),
                        contentDescription = stringResource(R.string.back)
                    )
                }
                Text(
                    text = stringResource(R.string.search_destinations),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(mediumSpace))

            OutlinedTextField(
                value = state.query,
                onValueChange = { onQueryChanged(it) },
                placeholder = { Text(stringResource(R.string.search_destination)) },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (state.query.isNotBlank()) {
                        IconButton(onClick = { onQueryChanged("") }) {
                            Icon(
                                painter = painterResource(id = R.drawable.close_icon),
                                contentDescription = "Clear"
                            )
                        }
                    }
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(smallSpace))

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.query.length > 1 && state.cityList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = stringResource(R.string.no_destination_found))
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.cityList) { city ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onCitySelected(city) }
                                .padding(vertical = smallSpace)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = city.name,
                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = city.countryName,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                Text(
                                    text = city.type,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier
                                        .background(
                                            MaterialTheme.colorScheme.surfaceVariant,
                                            shape = MaterialTheme.shapes.small
                                        )
                                        .padding(horizontal = smallSpace, vertical = smallSpace)
                                )
                            }
                        }
                    }
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
    MaterialTheme {
        CitySearchContent(
            state = CitySearchState(
                query = "Par",
                cityList = listOf(
                    City(name = "Paris", countryName = "France", iataCode = "PAR", type = "City"),
                    City(name = "Paris, Texas", countryName = "USA", iataCode = "PRX", type = "City")
                ),
                isLoading = false,
                errorMessage = null
            ),
            onQueryChanged = {},
            onCitySelected = {},
            snackBarHostState = remember { SnackbarHostState() },
            navigateBack = {}
        )
    }
}