package com.example.finalproject.presentation.ui.screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.finalproject.presentation.ui.screen.components.Dimensions.mediumSpace

@Composable
fun IconDropdownMenuItem(
    iconResource: Int,
    label: String,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = iconResource),
                    contentDescription = null,
                    modifier = Modifier.size(mediumSpace),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(label)
            }
        },
        onClick = onClick
    )
}