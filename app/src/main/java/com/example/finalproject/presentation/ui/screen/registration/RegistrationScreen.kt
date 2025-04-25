package com.example.finalproject.presentation.ui.screen.registration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.finalproject.R
import com.example.finalproject.presentation.ui.screen.components.CollectEffect
import com.example.finalproject.presentation.ui.screen.components.Dimensions.bigSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.mediumSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.smallSpace
import com.example.finalproject.presentation.ui.screen.components.LanguagePicker
import com.example.finalproject.presentation.ui.screen.components.StyledButton
import com.example.finalproject.presentation.ui.screen.components.StyledTextField
import com.example.finalproject.presentation.ui.theme.Black
import com.example.finalproject.presentation.ui.theme.Gray
import com.example.finalproject.presentation.ui.theme.Red

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit
){
    val state by viewModel.viewState.collectAsState()
    val snackBarHostState = remember {SnackbarHostState()}

    CollectEffect(flow = viewModel.effects) { effect ->
        when (effect) {
            is RegistrationEffect.NavigateToLogin -> navigateToLogin()
            is RegistrationEffect.ShowSnackBar -> snackBarHostState.showSnackbar(effect.message)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        RegistrationContent(
            state = state,
            onEvent = viewModel::obtainEvent
        )

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun RegistrationContent(
    state: RegistrationState,
    onEvent: (RegistrationEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(mediumSpace),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Spacer(modifier = Modifier.height(bigSpace))
            LanguagePicker(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(bigSpace))

            Text(
                text = "wanderlog",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = Red
            )

            Spacer(modifier = Modifier.height(bigSpace))
        }

        item {
            StyledButton(
                text = stringResource(R.string.sign_up_with_facebook),
                icon = painterResource(id = R.drawable.facebook_ic)
            ) {}
            Spacer(modifier = Modifier.height(smallSpace))

            StyledButton(
                text = stringResource(R.string.sign_up_with_google),
                icon = painterResource(id = R.drawable.google_ic)
            ){}
            Spacer(modifier = Modifier.height(mediumSpace))
        }

        if (state.showEmailFields) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = smallSpace)
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.or),
                        color = Gray,
                        modifier = Modifier.padding(horizontal = smallSpace)
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f))
                }
            }

            item {
                StyledTextField(
                    value = state.fullName,
                    onValueChange = { onEvent(RegistrationEvent.OnFullNameChanged(it)) },
                    label = stringResource(R.string.full_name),
                    isError = state.errorMessage?.contains(stringResource(R.string.full_name), ignoreCase = true) == true,
                    errorMessage = if (state.errorMessage?.contains(stringResource(R.string.full_name), ignoreCase = true) == true) state.errorMessage else null,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(smallSpace))

                StyledTextField(
                    value = state.email,
                    onValueChange = { onEvent(RegistrationEvent.OnEmailChanged(it)) },
                    label = stringResource(R.string.email),
                    isError = state.errorMessage?.contains(stringResource(R.string.email), ignoreCase = true) == true,
                    errorMessage = if (state.errorMessage?.contains(stringResource(R.string.email), ignoreCase = true) == true) state.errorMessage else null,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(smallSpace))

                StyledTextField(
                    value = state.password,
                    onValueChange = { onEvent(RegistrationEvent.OnPasswordChanged(it)) },
                    label = stringResource(R.string.password),
                    isPassword = true,
                    isVisible = state.showPassword,
                    onVisibilityToggle = { onEvent(RegistrationEvent.OnTogglePasswordVisibility) },
                    isError = state.errorMessage?.contains(stringResource(R.string.password), ignoreCase = true) == true,
                    errorMessage = if (state.errorMessage?.contains(stringResource(R.string.password), ignoreCase = true) == true) state.errorMessage else null,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(smallSpace))
            }
        }

        item {
            StyledButton(
                text = stringResource(R.string.sign_up_with_email),
                icon = painterResource(id = R.drawable.email_ic)
            ) {
                if (!state.showEmailFields) {
                    onEvent(RegistrationEvent.ToggleEmailFieldsVisibility)
                } else {
                    onEvent(RegistrationEvent.OnSubmit)
                }
            }
            Spacer(modifier = Modifier.height(mediumSpace))

            Text(
                text = stringResource(R.string.already_have_an_account_sign_in),
                color = Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onEvent(RegistrationEvent.NavigateToLogin) }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RegistrationScreenPreview(){
    RegistrationScreen(
        navigateToLogin = {}
    )
}