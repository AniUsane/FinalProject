package com.example.finalproject.presentation.ui.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.finalproject.R
import com.example.finalproject.presentation.ui.screen.components.Dimensions.bigSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.smallSpace
import com.example.finalproject.presentation.ui.screen.components.SettingsItem
import com.example.finalproject.presentation.ui.theme.White

@Composable
fun SettingsScreen(
    navigateToAccount: () -> Unit,
    navigateToPreferences: () -> Unit,
    navigateBack: () -> Unit
) {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(White)
        .padding(top = bigSpace)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(smallSpace)
        ){
            IconButton(onClick = navigateBack,
                modifier = Modifier.align(Alignment.TopStart)) {
                Icon(painterResource(R.drawable.back_arrow) , contentDescription = stringResource(R.string.back))
            }
            Text(
                stringResource(R.string.settings),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.Center))
        }

        HorizontalDivider()

        SettingsItem(stringResource(R.string.account), onClick = navigateToAccount)
        SettingsItem(stringResource(R.string.user_preferences), onClick = navigateToPreferences)

        Spacer(modifier = Modifier.weight(1f))
    }
}


@Composable
@Preview
fun SettingsScreenPreview(){
    SettingsScreen(
        navigateToAccount = {},
        navigateToPreferences = {},
        navigateBack = {}
    )
}