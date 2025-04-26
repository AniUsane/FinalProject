package com.example.finalproject.presentation.ui.screen.settings.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.example.finalproject.R
import com.example.finalproject.presentation.ui.screen.auth.components.CollectEffect
import com.example.finalproject.presentation.ui.screen.auth.components.Dimensions.bigSpace
import com.example.finalproject.presentation.ui.screen.auth.components.Dimensions.mediumSpace
import com.example.finalproject.presentation.ui.screen.auth.components.Dimensions.profileImage
import com.example.finalproject.presentation.ui.screen.auth.components.Dimensions.smallSpace
import com.example.finalproject.presentation.ui.screen.auth.components.StyledTextField
import com.example.finalproject.presentation.ui.theme.DarkGray
import com.example.finalproject.presentation.ui.theme.LightGray
import com.example.finalproject.presentation.ui.theme.OrangeColor
import com.example.finalproject.presentation.ui.theme.White

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToLogin: () -> Unit
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.obtainEvent(AccountEvent.LoadAccount)
    }

    CollectEffect(flow = viewModel.effects) { effect ->
        when (effect) {
            is AccountEffect.ShowSnackBar -> {
                snackBarHostState.showSnackbar(effect.message)
            }
            is AccountEffect.NavigateToLogin -> navigateToLogin()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        AccountContent(
            state = state,
            onEvent = viewModel::obtainEvent,
            navigateBack = navigateBack,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun AccountContent(
    state: AccountState,
    onEvent: (AccountEvent) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .padding(horizontal = mediumSpace),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(smallSpace)
        ) {
            IconButton(
                onClick = navigateBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(painterResource(R.drawable.back_arrow), contentDescription = stringResource(R.string.back))
            }

            Text(
                text = stringResource(R.string.account),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        HorizontalDivider()

        Box(
            modifier = Modifier
                .padding(vertical = mediumSpace)
                .size(profileImage)
                .background(LightGray, CircleShape),
            contentAlignment = Alignment.Center
        ) {

            val shouldShowInitials = state.profileImageUrl.isNullOrBlank() ||
                    state.profileImageUrl.equals("null", ignoreCase = true) ||
                    !state.profileImageUrl.startsWith("http")

            if (shouldShowInitials) {
                Text(
                    text = state.nameInitial,
                    style = MaterialTheme.typography.headlineMedium,
                    color = White
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(state.profileImageUrl),
                    contentDescription = stringResource(R.string.profile_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
        }

        StyledTextField(
            value = state.name,
            onValueChange = { onEvent(AccountEvent.NameChanged(it)) },
            label = stringResource(R.string.full_name),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = smallSpace)
        )

        StyledTextField(
            value = state.username,
            onValueChange = { onEvent(AccountEvent.UsernameChanged(it)) },
            label = stringResource(R.string.username),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = smallSpace)
        )

        StyledTextField(
            value = state.email,
            onValueChange = { onEvent(AccountEvent.EmailChanged(it)) },
            label = stringResource(R.string.email),
            isError = state.emailError != null,
            errorMessage = state.emailError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = smallSpace)
        )

        val isSaveEnabled = (state.name != state.fullName) ||
                (state.email != state.user?.email) ||
                (state.username != state.profile?.username)

        TextButton(
            onClick = { onEvent(AccountEvent.SaveClicked) },
            enabled = isSaveEnabled && !state.isLoading,
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .background(
                    if (isSaveEnabled) OrangeColor else LightGray,
                    RoundedCornerShape(bigSpace)
                )
        ) {
            if(state.isLoading){
                CircularProgressIndicator(
                    modifier = Modifier.size(mediumSpace),
                    color = White
                )
            }else{
                Text(stringResource(R.string.save), color = White)
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = mediumSpace))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
        ){
            Icon(
                painter = painterResource(id = R.drawable.bin_icon),
                contentDescription = stringResource(R.string.delete_icon),
                tint = DarkGray,
                modifier = Modifier.size(mediumSpace)
            )

            Text(
                text = stringResource(R.string.delete_account),
                modifier = Modifier
                    .padding(start = mediumSpace)
                    .clickable { onEvent(AccountEvent.DeleteClicked) },
                color = DarkGray,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
fun AccountContentPreview() {
    AccountContent(
        state = AccountState(),
        onEvent = {},
        navigateBack = {}
    )
}