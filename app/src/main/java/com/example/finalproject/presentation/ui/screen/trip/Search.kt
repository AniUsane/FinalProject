package com.example.finalproject.presentation.ui.screen.trip

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        label = { Text("Search") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}


@Composable
fun SearchScreen() {
    var searchQuery by remember { mutableStateOf("") }

    Column {
        SearchBar(
            query = searchQuery,
            onQueryChanged = { searchQuery = it }
        )

        // You can filter your list based on `searchQuery` here
    }
}