package com.example.finalproject.presentation.ui.screen.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.example.finalproject.R
import com.example.finalproject.presentation.model.ProfileUi
import com.example.finalproject.presentation.ui.screen.components.CollectEffect
import com.example.finalproject.presentation.ui.screen.components.Dimensions.bigSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.mediumSpace

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    callbacks: ProfileScreenCallbacks
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val pickMediaLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            viewModel.obtainEvent(ProfileEvent.OnProfileImageSelected(it.toString()))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.obtainEvent(ProfileEvent.LoadProfile)
    }

    CollectEffect(flow = viewModel.effects) { effect ->
        when (effect) {
            is ProfileEffect.NavigateToLogin -> callbacks.navigateToLogin()
            is ProfileEffect.ShowSnackBar -> callbacks.showSnackBar(effect.message)
            is ProfileEffect.OpenImagePicker -> {
                pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            is ProfileEffect.ShowSettingsDialog -> callbacks.showSettingsDialog()
            is ProfileEffect.ShowHelpDialog -> callbacks.showHelpDialog()
            is ProfileEffect.ShowFeedbackDialog -> callbacks.showFeedbackDialog()
        }
    }

    when {
        state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        state.profile != null -> {
            ProfileContent(
                profile = state.profile!!,
                onEditImage = { viewModel.obtainEvent(ProfileEvent.ChangeImageClicked) },
                onLogout = { viewModel.obtainEvent(ProfileEvent.LogoutClicked) },
                onDeleteAccount = { viewModel.obtainEvent(ProfileEvent.DeleteAccount) },
                onMenuOptionSelected = { option ->
                    when (option) {
                        "Settings" -> viewModel.obtainEvent(ProfileEvent.SettingsClicked)
                        "Help & how to" -> viewModel.obtainEvent(ProfileEvent.HelpClicked)
                        "Feedback & support" -> viewModel.obtainEvent(ProfileEvent.FeedbackClicked)
                    }
                }
            )
        }

//        state.errorMessage != null -> {
//            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Text(state.errorMessage ?: "Something went wrong", color = Color.Red)
//            }
//        }

//        else -> {
//            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Text("No data available", color = Color.Gray)
//            }
//        }
    }
}


@Composable
fun ProfileContent(
    profile: ProfileUi,
    onEditImage: () -> Unit,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit,
    onMenuOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Trips", "Guides")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bigSpace)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = profile.profileImageUrl?.ifBlank{null},
                        error = painterResource(R.drawable.profile_img),
                        placeholder = painterResource(R.drawable.profile_img)),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                )

                IconButton(onClick = onEditImage) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Image")
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                }

                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Settings") },
                        onClick = {
                            expanded = false
                            onMenuOptionSelected("Settings")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Help & how to") },
                        onClick = {
                            expanded = false
                            onMenuOptionSelected("Help & how to")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Feedback & support") },
                        onClick = {
                            expanded = false
                            onMenuOptionSelected("Feedback & support")
                        }
                    )
                    HorizontalDivider()
                    DropdownMenuItem(
                        text = { Text("Log out") },
                        onClick = {
                            expanded = false
                            onLogout()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete account", color = Color.Red) },
                        onClick = {
                            expanded = false
                            onDeleteAccount()
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = profile.fullName, style = MaterialTheme.typography.headlineSmall)
        Text(text = "@${profile.username}", color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        TabRow(selectedTabIndex = selectedTab) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        when (selectedTab) {
            0 -> Text("Trips list here", modifier = Modifier.padding(16.dp))
            1 -> Text("Guides list here", modifier = Modifier.padding(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileContentPreview() {
    val dummyProfile = ProfileUi(
        id = "1",
        userId = "123",
        username = "john_doe",
        fullName = "John Doe",
        profileImageUrl = "https://via.placeholder.com/150",
        bio = "Explorer | Travel Enthusiast",
        trips = listOf("trip1", "trip2"),
        guides = listOf("guide1", "guide2")
    )

    MaterialTheme {
        ProfileContent(
            profile = dummyProfile,
            onEditImage = {},
            onLogout = {},
            onDeleteAccount = {},
            onMenuOptionSelected = {}
        )
    }
}