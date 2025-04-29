package com.example.finalproject.presentation.ui.screen.addGuide.foraefj

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.finalproject.presentation.ui.screen.components.CollectEffect
import com.example.finalproject.presentation.ui.screen.components.Dimensions.bigSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.mediumSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.smallSpace
import com.example.finalproject.presentation.ui.screen.components.StyledButton

@Composable
fun AddGuideScreen(
    viewModel: AddGuideViewModel = hiltViewModel(),
    navigateToCitySearch: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToGuide: (String) -> Unit,
    savedStateHandle: SavedStateHandle
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    val selectedCity by savedStateHandle.getStateFlow("city_name", "").collectAsStateWithLifecycle()
    LaunchedEffect(selectedCity) {
        if (selectedCity.isNotBlank()) {
            viewModel.obtainEvent(AddGuideEvent.OnCitySelected(selectedCity))
            savedStateHandle["city_name"] = ""
        }
    }

    CollectEffect(flow = viewModel.effects) { effect ->
        when (effect) {
            is AddGuideEffect.NavigateToProfile -> navigateToProfile()
            is AddGuideEffect.NavigateToGuide -> {
                savedStateHandle["guideId"] = effect.guideId
                navigateToGuide(effect.guideId)
            }
            is AddGuideEffect.ShowSnackBar -> snackBarHostState.showSnackbar(effect.message)

        }
    }

    AddGuideContent(
        state = state,
        onEvent = viewModel::obtainEvent,
        snackBarHostState = snackBarHostState,
        onCityFieldClick = navigateToCitySearch
    )
}

@Composable
fun AddGuideContent(
    state: AddGuideState,
    onEvent: (AddGuideEvent) -> Unit,
    snackBarHostState: SnackbarHostState,
    onCityFieldClick: () -> Unit
) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                onEvent(AddGuideEvent.OnUserImagesChanged(listOf(it)))
            }
        }
    )

    Box(modifier = Modifier.fillMaxSize().padding(mediumSpace)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = bigSpace),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Write a travel guide", style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(mediumSpace))
            Text("Share tips and recommendations for your favourite destinations",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(mediumSpace))

            OutlinedTextField(
                value = state.description,
                onValueChange = { onEvent(AddGuideEvent.OnDescriptionChanged(it)) },
                label = { Text("Guide description") },
                modifier = Modifier.fillMaxWidth().padding(vertical = smallSpace)
            )

            Text("Upload Photos", modifier = Modifier.padding(top = smallSpace))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.userImages) { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                item {
                    IconButton(onClick = {
                        imagePickerLauncher.launch("image/*")
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Upload")
                    }
                }
            }

            Spacer(modifier = Modifier.height(smallSpace))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium)
                    .clickable { onCityFieldClick() }
                    .padding(mediumSpace)
            ) {
                Text(text = if (state.city.isBlank()) "Where to?" else state.city)
            }

            Spacer(modifier = Modifier.height(mediumSpace))

            StyledButton(
                text = "Create Guide",
                onClick = { onEvent(AddGuideEvent.OnSubmitClicked) }
            )
        }

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
