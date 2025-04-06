package com.example.finalproject.presentation.ui.screen

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finalproject.presentation.ui.screen.components.StyledButton

@Composable
fun RegistrationScreen(){
    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Change language")
        //logo image
        Spacer(modifier = Modifier.height(200.dp))
        StyledButton(text = "Sign up with Facebook", icon = null){}
        Spacer(modifier = Modifier.height(10.dp))
        StyledButton(text = "Sign up with Google", icon = null){}
    }
}

@Composable
@Preview(showBackground = true)
fun RegistrationScreenPreview(){
    RegistrationScreen()
}