package com.example.finalproject.presentation.ui.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun StyledTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    isVisible: Boolean = false,
    onVisibilityToggle: () -> Unit = {},
    isError: Boolean = false,
    errorMessage: String? = null,
){
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            singleLine = true,
            isError = isError,
            visualTransformation = if (isPassword && !isVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (isPassword) {
                    val icon = if (isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = onVisibilityToggle) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                }
            },
            modifier = modifier
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}