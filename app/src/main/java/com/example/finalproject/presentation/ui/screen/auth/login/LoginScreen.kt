package com.example.finalproject.presentation.ui.screen.login

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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.finalproject.R
import com.example.finalproject.presentation.ui.screen.auth.components.CollectEffect
import com.example.finalproject.presentation.ui.screen.auth.components.Dimensions.bigSpace
import com.example.finalproject.presentation.ui.screen.auth.components.Dimensions.mediumSpace
import com.example.finalproject.presentation.ui.screen.auth.components.Dimensions.smallSpace
import com.example.finalproject.presentation.ui.screen.auth.components.StyledButton
import com.example.finalproject.presentation.ui.screen.auth.components.StyledTextField
import com.example.finalproject.presentation.ui.screen.auth.login.LoginEffect
import com.example.finalproject.presentation.ui.screen.auth.login.LoginEvent
import com.example.finalproject.presentation.ui.screen.auth.login.LoginState
import com.example.finalproject.presentation.ui.screen.auth.login.LoginViewModel
import com.example.finalproject.presentation.ui.screen.components.LanguagePicker
import com.example.finalproject.presentation.ui.theme.Black
import com.example.finalproject.presentation.ui.theme.Gray
import com.example.finalproject.presentation.ui.theme.LightGray
import com.example.finalproject.presentation.ui.theme.OrangeColor
import com.example.finalproject.presentation.ui.theme.Red
import com.example.finalproject.presentation.ui.theme.White


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToRegister: () -> Unit,
    navigateToProfile: () -> Unit
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    CollectEffect(flow = viewModel.effects) { effect ->
        when (effect) {
            is LoginEffect.NavigateToRegister -> navigateToRegister()
            is LoginEffect.NavigateToHome -> navigateToProfile()
        }
    }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            snackBarHostState.showSnackbar(it)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LoginContent(
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
fun LoginContent(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
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
                text = stringResource(R.string.sign_in_with_facebook),
                icon = painterResource(id = R.drawable.facebook_ic)
            ) {}
            Spacer(modifier = Modifier.height(smallSpace))

            StyledButton(
                text = stringResource(R.string.sign_in_with_google),
                icon = painterResource(id = R.drawable.google_ic)
            ) {}

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
                    value = state.email,
                    onValueChange = { onEvent(LoginEvent.OnEmailChanged(it)) },
                    label = stringResource(R.string.email),
                    isError = state.emailError != null,
                    errorMessage = state.emailError,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(smallSpace))

                StyledTextField(
                    value = state.password,
                    onValueChange = { onEvent(LoginEvent.OnPasswordChanged(it)) },
                    label = stringResource(R.string.password),
                    isPassword = true,
                    isVisible = state.showPassword,
                    onVisibilityToggle = { onEvent(LoginEvent.OnTogglePasswordVisibility) },
                    isError = state.passwordError != null,
                    errorMessage = state.passwordError,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(smallSpace))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = smallSpace)
                ) {
                    Checkbox(
                        checked = state.rememberMe,
                        colors = CheckboxDefaults.colors(
                            checkedColor = OrangeColor,
                            checkmarkColor = White,
                            uncheckedColor = OrangeColor,
                            disabledCheckedColor = LightGray,
                            disabledUncheckedColor = LightGray
                        ),
                        onCheckedChange = { onEvent(LoginEvent.OnRememberMeChecked(it)) }
                    )
                    Text(text = stringResource(R.string.remember_me))
                }
            }
        }

        item {
            StyledButton(
                text = stringResource(R.string.sign_in_with_email),
                icon = painterResource(id = R.drawable.email_ic)
            ) {
                if (!state.showEmailFields) {
                    onEvent(LoginEvent.OnToggleEmailVisibility)
                } else {
                    onEvent(LoginEvent.OnSubmit)
                }
            }

            Spacer(modifier = Modifier.height(mediumSpace))

            Text(
                text = stringResource(R.string.don_t_have_an_account_sign_up),
                color = Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onEvent(LoginEvent.NavigateToRegister) }
            )
        }
    }
}
@Composable
@Preview(showBackground = true)
fun LoginContentPreview() {
    val previewState = LoginState(
        email = "user@example.com",
        password = "password123",
        showEmailFields = true,
        showPassword = false,
        rememberMe = true,
        emailError = null,
        passwordError = null,
        errorMessage = null
    )

    LoginContent(
        state = previewState,
        onEvent = {}
    )
}