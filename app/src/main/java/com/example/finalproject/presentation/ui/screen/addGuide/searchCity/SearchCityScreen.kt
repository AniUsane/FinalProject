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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.finalproject.R
import com.example.finalproject.domain.model.bookHotel.City
import com.example.finalproject.presentation.ui.screen.components.CollectEffect
import com.example.finalproject.presentation.ui.screen.components.Dimensions.bigSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.mediumSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.smallSpace

@Composable
fun SearchCityScreen(
    navigateBack: () -> Unit,
    onCitySelected: (City) -> Unit,
    viewModel: SearchCityViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    CollectEffect(flow = viewModel.effects) { effect ->
        if (effect is SearchCityEffect.ShowSnackBar) {
            snackBarHostState.showSnackbar(effect.message)
        }
    }

    SearchCityContent(
        state = state,
        onQueryChanged = { viewModel.obtainEvent(SearchCityEvent.OnQueryChanged(it)) },
        onCitySelected = { city ->
            onCitySelected(city)
        },
        snackBarHostState = snackBarHostState,
        navigateBack = navigateBack
    )
}

@Composable
fun SearchCityContent(
    state: SearchCityState,
    onQueryChanged: (String) -> Unit,
    onCitySelected: (City) -> Unit,
    snackBarHostState: SnackbarHostState,
    navigateBack: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val paddingValue = if (isLandscape) bigSpace else mediumSpace

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = paddingValue)
            .padding(top = bigSpace)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                OutlinedTextField(
                    value = state.query,
                    onValueChange = { onQueryChanged(it) },
                    placeholder = { Text("Search destination...") },
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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
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

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}