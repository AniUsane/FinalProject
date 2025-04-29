package com.example.finalproject.presentation.ui.screen.trip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject.presentation.ui.theme.Orange
import com.example.finalproject.presentation.ui.theme.PostLightGreyColor
import com.example.finalproject.presentation.ui.theme.UnselectedBottomIcon
import com.example.finalproject.presentation.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TripScreen(onNavigateBack: () -> Unit) {
    var showDatePicker by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    )

    {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {onNavigateBack()},
                    modifier = Modifier
                        .padding(top = 15.dp, start = 10.dp)
                        .size(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = UnselectedBottomIcon
                    ),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = "navigate back",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = "Plan a new trip",
                    textAlign = TextAlign.Center,
                    style = TextStyle(color = Color.Black, fontSize = 32.sp, fontWeight = Bold),
                    modifier = Modifier
                        .padding(top = 20.dp, end = 58.dp)
                        .weight(1f)
                )
            }
            Spacer(modifier = Modifier
                .height(20.dp)
                .fillMaxWidth())

            Text(
                "Build an itinerary and map out your upcoming travel plans",
                maxLines = 2,
                modifier = Modifier
                    .width(320.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                color = PostLightGreyColor,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier
                .height(30.dp)
                .fillMaxWidth())


            var startingDate by remember { mutableStateOf("Start date") }
            var endingDate by remember { mutableStateOf("End date") }

            var selectedDateRange by remember { mutableStateOf<Pair<Long?, Long?>?>(null) }

            if (selectedDateRange != null) {
                val (startMillis, endMillis) = selectedDateRange!!
                val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val startDate = startMillis?.let { formatter.format(Date(it)) } ?: "N/A"
                val endDate = endMillis?.let { formatter.format(Date(it)) } ?: "N/A"
                startingDate = startDate
                endingDate = endDate
            }

            if (showDatePicker)
                DateRangePickerModal(onDateRangeSelected = {
                    selectedDateRange = it
                    showDatePicker = false
                }, onDismiss = { showDatePicker = false })

            TripInputs({ }, {
                showDatePicker = true
            },
                startDate = startingDate,
                endDate = endingDate
            )


        }

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange, // background
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
        ) {

            Text(
                text = "Start planning",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )

        }
    }


}



@Preview
@Composable
fun NewTripPreview() {
    TripScreen() {}
}


@Composable
fun TripInputs(
    onWhereToClick: () -> Unit,
    onDatesClick: () -> Unit,
    startDate: String,
    endDate: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Where to?
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .height(63.dp)
                .border(
                    width = 1.dp,
                    color = UnselectedBottomIcon,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clickable { onWhereToClick() },
            contentAlignment = Alignment.CenterStart,


            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Where to?",
                    fontWeight = Bold,
                    color = Color.Black,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "e.g., Paris, Hawaii, Japan",
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            }
        }

        // Dates (optional)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = UnselectedBottomIcon,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clickable { onDatesClick() }
        ) {
            Column {
                Text(
                    text = "Dates (optional)",
                    fontWeight = Bold,
                    color = Color.Black,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    DateInputField(startDate)
                    DateInputField(endDate)
                }
            }
        }
    }
}


@Composable
fun DateInputField(placeholder: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.CalendarToday,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = placeholder,
            color = Color.Gray,
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
fun InputFieldsPreview() {

}