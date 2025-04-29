package com.example.finalproject.presentation.ui.screen.addGuide.searchCity

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.example.finalproject.R
import com.example.finalproject.domain.model.bookHotel.City
import com.example.finalproject.presentation.ui.screen.components.CollectEffect
import com.example.finalproject.presentation.ui.screen.components.Dimensions.bigSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.mediumSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.smallSpace

@Composable
fun SearchCityScreen(
    navigateBack: () -> Unit,
    onCitySelected: (CityNavigationModel) -> Unit
) {
    val viewModel: SearchCityViewModel = hiltViewModel()
    val state = viewModel.viewState.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    CollectEffect(flow = viewModel.effects) { effect ->
        when (effect) {
            is SearchCityEffect.GuideCreatedSuccessfully -> {
            }
            is SearchCityEffect.ShowSnackBar -> {
                snackBarHostState.showSnackbar(effect.message)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SearchCityContent(
            state = state,
            onQueryChanged = { viewModel.obtainEvent(SearchCityEvent.OnQueryChanged(it)) },
            onCitySelected = { city ->
                val cityResult = CityNavigationModel(
                    id = city.id,
                    name = city.name,
                    countryName = city.countryName,
                    type = city.type
                )
                onCitySelected(cityResult) // ✅ call the lambda
            },
            navigateBack = navigateBack
        )

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun SearchCityContent(
    state: SearchCityState,
    onQueryChanged: (String) -> Unit,
    onCitySelected: (City) -> Unit,
    navigateBack: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val paddingValue = if (isLandscape) bigSpace else mediumSpace

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = paddingValue).padding(top = bigSpace)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            item {
                OutlinedTextField(
                    value = state.query,
                    onValueChange = { onQueryChanged(it) },
                    placeholder = { Text(text = "Search destination...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = mediumSpace),
                    singleLine = true,
                    leadingIcon = {
                        IconButton(onClick = navigateBack) {
                            Icon(
                                painter = painterResource(id = R.drawable.back_arrow),
                                contentDescription = "Back"
                            )
                        }
                    },
                    trailingIcon = {
                        if (state.query.isNotBlank()) {
                            IconButton(onClick = { onQueryChanged("") }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.close_icon),
                                    contentDescription = "Clear"
                                )
                            }
                        }
                    }
                )
            }

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
