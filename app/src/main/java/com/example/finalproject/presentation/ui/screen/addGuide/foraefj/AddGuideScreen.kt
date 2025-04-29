package com.example.finalproject.presentation.ui.screen.addGuide.foraefj

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.domain.model.bookHotel.City
import com.example.finalproject.presentation.ui.screen.components.CollectEffect
import com.example.finalproject.presentation.ui.screen.components.Dimensions.bigSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.mediumSpace
import com.example.finalproject.presentation.ui.theme.OrangeColor
import com.example.finalproject.presentation.ui.theme.White

@Composable
fun AddGuideScreen(
    navigateToSearchCity: () -> Unit,
    savedStateHandle: SavedStateHandle
) {
    val viewModel: AddGuideViewModel = hiltViewModel()
    val state = viewModel.viewState.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<String?>("city_name", null)
            ?.collect { cityName ->
                cityName?.let {
                    viewModel.obtainEvent(AddGuideEvent.OnCitySelected(it))
                    navController.currentBackStackEntry?.savedStateHandle?.remove<String>("city_name")
                }
            }
    }

    CollectEffect(flow = viewModel.effects) { effect ->
        when (effect) {
            is AddGuideEffect.NavigateToSearchCityScreen -> navigateToSearchCity()
            is AddGuideEffect.GuideCreatedSuccessfully -> { /* Navigate after create if you want */ }
            is AddGuideEffect.ShowSnackBar -> snackBarHostState.showSnackbar(effect.message)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AddGuideContent(
            state = state,
            onFieldClicked = { viewModel.obtainEvent(AddGuideEvent.OnSearchCityFieldClicked) },
            onCreateGuideClicked = { viewModel.obtainEvent(AddGuideEvent.OnCreateGuideClicked) }
        )

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun AddGuideContent(
    state: AddGuideState,
    onFieldClicked: () -> Unit,
    onCreateGuideClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .padding(top = bigSpace)
    ) {
        Text(
            text = "Where to?",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onFieldClicked()
                }
        ) {
            OutlinedTextField(
                value = state.query,
                onValueChange = {},
                placeholder = { Text("Search destination...") },
                readOnly = true,
                enabled = false, // important
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = { onCreateGuideClicked() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = mediumSpace)
                .fillMaxWidth(0.5f),
            colors = ButtonDefaults.buttonColors(containerColor = OrangeColor)
        ) {
            Text(
                text = "Create guide",
                color = White,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}