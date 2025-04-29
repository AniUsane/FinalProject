package com.example.finalproject.presentation.ui.screen.profile

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.example.finalproject.R
import com.example.finalproject.presentation.model.addGuide.GuideDataUi
import com.example.finalproject.presentation.model.addGuide.GuideUi
import com.example.finalproject.presentation.model.user.ProfileUi
import com.example.finalproject.presentation.ui.screen.components.CollectEffect
import com.example.finalproject.presentation.ui.screen.components.Dimensions.mediumSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.profileImage
import com.example.finalproject.presentation.ui.screen.components.Dimensions.smallSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.thirtyDp
import com.example.finalproject.presentation.ui.screen.components.EmptyState
import com.example.finalproject.presentation.ui.screen.components.IconDropdownMenuItem
import com.example.finalproject.presentation.ui.theme.Gray
import com.example.finalproject.presentation.ui.theme.LightGray
import com.example.finalproject.presentation.ui.theme.OrangeColor
import com.example.finalproject.presentation.ui.theme.PinkBackground
import com.example.finalproject.presentation.ui.theme.White

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
            is ProfileEffect.NavigateToAddGuide -> callbacks.navigateToAddGuide()
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
                },
                onAddTrip = { viewModel.obtainEvent(ProfileEvent.AddTrip) },
                onAddGuide = { viewModel.obtainEvent(ProfileEvent.AddGuide) },
                onGuideClick = callbacks.onGuideClick
            )
        }

        state.errorMessage != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(state.errorMessage ?: "Something went wrong", color = Color.Red)
            }
        }

        else -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No data available", color = Color.Gray)
            }
        }
    }
}


@Composable
fun ProfileContent(
    profile: ProfileUi,
    onEditImage: () -> Unit,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit,
    onMenuOptionSelected: (String) -> Unit,
    onAddTrip: () -> Unit,
    onAddGuide: () -> Unit,
    onGuideClick: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf(stringResource(R.string.trips), stringResource(R.string.guides))
    var sortDialogVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(PinkBackground)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 120.dp)
                .background(
                    White,
                    shape = RoundedCornerShape(topStart = mediumSpace, topEnd = mediumSpace)
                )
        ) {
            Spacer(modifier = Modifier.height(profileImage))

            Text(
                text = profile.fullName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "@${profile.username}",
                color = Gray,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(smallSpace))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("0", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                    Text("FOLLOWERS", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("0", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                    Text("FOLLOWING", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(mediumSpace))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = mediumSpace),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.sort), color = Gray)
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = White,
                    contentColor = OrangeColor,
                    indicator = { tabPositions ->
                        SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = OrangeColor
                        )
                    }
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    color = if (selectedTab == index) OrangeColor else Gray
                                )
                            }
                        )
                    }
                }
                TextButton(onClick = { sortDialogVisible = true }) {
                    Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = null, tint = Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(stringResource(R.string.sort), color = Gray)
                }
            }

            when (selectedTab) {
                0 -> EmptyState(
                    message = stringResource(R.string.you_haven_t_planned_any_trips_yet),
                    buttonText = stringResource(R.string.start_planning_a_trip),
                    onClick = onAddTrip
                )
                1 -> {
                    if (profile.guide.isEmpty()) {
                        EmptyState(
                            message = stringResource(R.string.you_haven_t_added_any_guides_yet),
                            buttonText = stringResource(R.string.add_a_guide),
                            onClick = onAddGuide
                        )
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(profile.guide) { guide ->
                                GuideItem(
                                    guideName = guide.data.title ?: "Untitled Guide",
                                    onClick = { onGuideClick(guide.id) }
                                )
                            }
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(70.dp))
                Box(contentAlignment = Alignment.BottomEnd) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = profile.profileImageUrl?.ifBlank { null },
                            error = painterResource(R.drawable.profile_img),
                            placeholder = painterResource(R.drawable.profile_img),
                        ),
                        contentDescription = stringResource(R.string.profile_image),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                            .background(LightGray)
                    )

                    IconButton(
                        onClick = onEditImage,
                        modifier = Modifier
                            .size(24.dp)
                            .background(White, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.edit_image),
                            modifier = Modifier.size(mediumSpace),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = thirtyDp), contentAlignment = Alignment.TopEnd) {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.menu))
            }

            val context = LocalContext.current

            val localMenuHandler: (MenuOption) -> Unit = { option ->
                when (option) {
                    MenuOption.HelpHowTo -> {
                        val intent = Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://help.wanderlog.com/hc/en-us"))
                        context.startActivity(intent)
                    }
                    MenuOption.FeedbackSupport -> {
                        val intent = Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://wanderlog.com/chatSupport?loginToken=VKUCpNFLQ3miKlQhXziuZx72PXqUJXLI"))
                        context.startActivity(intent)
                    }

                    else -> onMenuOptionSelected(option.toString())
                }
            }

            DropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(x = -smallSpace, y = 0.dp)
            ) {
                IconDropdownMenuItem(
                    iconResource = R.drawable.settings_icon,
                    label = stringResource(R.string.settings),
                    onClick = {
                        expanded = false
                        localMenuHandler(MenuOption.Settings)
                    }
                )

                IconDropdownMenuItem(
                    iconResource = R.drawable.info_icon,
                    label = stringResource(R.string.help_how_to),
                    onClick = {
                        expanded = false
                        localMenuHandler(MenuOption.HelpHowTo)
                    }
                )

                IconDropdownMenuItem(
                    iconResource = R.drawable.question_icon,
                    label = stringResource(R.string.feedback_support),
                    onClick = {
                        expanded = false
                        localMenuHandler(MenuOption.HelpHowTo)
                    }
                )

                IconDropdownMenuItem(
                    iconResource = R.drawable.logout_icon,
                    label = stringResource(R.string.log_out),
                    onClick = {
                        expanded = false
                        onLogout()
                    }
                )

                IconDropdownMenuItem(
                    iconResource = R.drawable.bin_icon,
                    label = stringResource(R.string.delete_account),
                    onClick = {
                        expanded = false
                        onDeleteAccount()
                    }
                )
            }
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
        guide = listOf(
            GuideUi(
                id = "guide1",
                userId = "123",
                location = "Paris",
                data = GuideDataUi(
                    title = "Paris Travel Tips",
                    description = "Best spots in Paris",
                    imageUrl = "https://via.placeholder.com/150"
                ),
                createdAt = "2024-01-01"
            ),
            GuideUi(
                id = "guide2",
                userId = "123",
                location = "London",
                data = GuideDataUi(
                    title = "London City Guide",
                    description = "Explore history and pubs",
                    imageUrl = "https://via.placeholder.com/150"
                ),
                createdAt = "2024-02-01"
            )
        )
    )

    MaterialTheme {
        ProfileContent(
            profile = dummyProfile,
            onEditImage = {},
            onLogout = {},
            onDeleteAccount = {},
            onMenuOptionSelected = {},
            onAddTrip = {},
            onAddGuide = {},
            onGuideClick = {}
        )
    }
}