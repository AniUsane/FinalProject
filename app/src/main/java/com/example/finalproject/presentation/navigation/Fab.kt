package com.example.finalproject.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ShowButtons() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp) // Stay above BottomAppBar
    ) {
        Text(text = "Nini", style = TextStyle(color = Color.White))

        // Semi-transparent background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x005C5C5C).copy(alpha = 0.5f))
                .clickable{

                }
        ) {
        }


    }
}


@Preview
@Composable
fun ShowButtonsPreview() {
    ShowButtons()
}