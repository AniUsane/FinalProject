package com.example.finalproject.presentation.ui.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.finalproject.presentation.ui.screen.components.Dimensions.mediumSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.smallSpace
import com.example.finalproject.presentation.ui.screen.components.LanguagePicker
import com.example.finalproject.presentation.ui.theme.White

@Composable
fun PreferencesScreen(
    navigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(top = mediumSpace)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(smallSpace)
        ) {
            IconButton(
                onClick = navigateBack,
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    painterResource(R.drawable.back_arrow),
                    contentDescription = stringResource(R.string.back)
                )
            }
            Text(
                stringResource(R.string.user_preferences),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        HorizontalDivider()

        Text(
            text = stringResource(R.string.choose_language),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    smallSpace
                ),
            style = MaterialTheme.typography.titleMedium
        )
        LanguagePicker(modifier = Modifier
            .fillMaxWidth()
            .padding(smallSpace))

    }
}

@Composable
@Preview
fun PreferencesScreenPreview(){
    PreferencesScreen(
        navigateBack = {}
    )
}