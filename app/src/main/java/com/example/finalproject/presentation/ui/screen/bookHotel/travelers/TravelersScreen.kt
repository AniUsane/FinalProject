package com.example.finalproject.presentation.ui.screen.bookHotel.travelers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.finalproject.R
import com.example.finalproject.presentation.model.bookHotel.Travelers
import com.example.finalproject.presentation.ui.screen.components.Dimensions.mediumSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.smallSpace
import com.example.finalproject.presentation.ui.theme.Black
import com.example.finalproject.presentation.ui.theme.OrangeColor
import com.example.finalproject.presentation.ui.theme.White

@Composable
fun TravelersScreen(
    onSaveTravelers: (Travelers) -> Unit
) {
    var adults by remember { mutableStateOf(2) }
    var children by remember { mutableStateOf(0) }
    var rooms by remember { mutableStateOf(1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(mediumSpace),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(R.string.adults, adults), color = Black)
            Row {
                Button(onClick = { if (adults > 1) adults-- }) { Text(stringResource(R.string.minus_sign)) }
                Spacer(modifier = Modifier.width(smallSpace))
                Button(onClick = { adults++ }) { Text(stringResource(R.string.plus_sign)) }
            }

            Spacer(modifier = Modifier.height(mediumSpace))

            Text(text = stringResource(R.string.children, children), color = Black)
            Row {
                Button(onClick = { if (children > 0) children-- }) { Text(stringResource(R.string.minus_sign)) }
                Spacer(modifier = Modifier.width(smallSpace))
                Button(onClick = { children++ }) { Text(stringResource(R.string.plus_sign)) }
            }

            Spacer(modifier = Modifier.height(mediumSpace))

            Text(text = stringResource(R.string.rooms, rooms), color = Black)
            Row {
                Button(onClick = { if (rooms > 1) rooms-- }) { Text(stringResource(R.string.minus_sign)) }
                Spacer(modifier = Modifier.width(smallSpace))
                Button(onClick = { rooms++ }) { Text(stringResource(R.string.plus_sign)) }
            }

            Spacer(modifier = Modifier.height(mediumSpace))

            Button(
                onClick = {
                    onSaveTravelers(Travelers(rooms, adults, children))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = OrangeColor
                )
            ) {
                Text(text = stringResource(R.string.save), color = White)
            }
        }
    }
}
