package com.example.finalproject.presentation.ui.screen.bookHotel.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.finalproject.R
import com.example.finalproject.presentation.ui.screen.components.Dimensions.bigSpace
import com.example.finalproject.presentation.ui.screen.components.Dimensions.mediumSpace
import com.example.finalproject.presentation.ui.screen.components.toLocalDate
import com.example.finalproject.presentation.ui.theme.Black
import com.example.finalproject.presentation.ui.theme.OrangeColor
import com.example.finalproject.presentation.ui.theme.White
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseDateScreen(
    startDate: LocalDate? = null,
    endDate: LocalDate? = null,
    onDoneClicked: (Pair<LocalDate, LocalDate>) -> Unit,
    onBackPressed: () -> Unit
) {
    val datePickerState = rememberDateRangePickerState()

    LaunchedEffect(Unit) {
        startDate?.let {
            val millis = it.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            datePickerState.setSelection(millis, datePickerState.selectedEndDateMillis)
        }
        endDate?.let {
            val millis = it.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            datePickerState.setSelection(datePickerState.selectedStartDateMillis, millis)
        }
    }

    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = OrangeColor,
            surface = White,
            onSurface = Black,
            outline = OrangeColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(horizontal = mediumSpace)
        ) {
            Spacer(modifier = Modifier.height(bigSpace))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = {
                    onBackPressed()
                }) {
                    Text(text = stringResource(R.string.cancel), color = Black)
                }

                Text(
                    text = stringResource(R.string.stay_duration),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Black
                )

                TextButton(onClick = {
                    val start = datePickerState.selectedStartDateMillis?.toLocalDate()
                    val end = datePickerState.selectedEndDateMillis?.toLocalDate()
                    if (start != null && end != null) {
                        onDoneClicked(start to end)
                    }
                }) {
                    Text(text = stringResource(R.string.done), color = OrangeColor)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            DateRangePicker(
                state = datePickerState,
                showModeToggle = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(8.dp)
            )
        }
    }
}

