package com.example.finalproject.presentation.ui.screen.components

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.example.finalproject.R
import com.example.finalproject.presentation.ui.theme.TransparentColor

@Composable
fun LanguagePicker(modifier: Modifier = Modifier){
    val languages = listOf(
        "English" to "en",
        "ქართული" to "ka"
    )
    var expanded by remember { mutableStateOf(false) }

    Column(modifier) {
        Button(modifier = Modifier.fillMaxWidth().background(TransparentColor),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            onClick = { expanded = true }) {
            Text(stringResource(R.string.english))
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false },
            offset = DpOffset(x = 120.dp, y = 0.dp)
        ) {
            languages.forEach { (label, tag) ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        expanded = false
                        AppCompatDelegate.setApplicationLocales(
                            LocaleListCompat.forLanguageTags(tag)
                        )
                    }
                )
            }
        }
    }
}