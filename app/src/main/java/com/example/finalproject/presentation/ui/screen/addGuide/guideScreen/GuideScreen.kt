package com.example.finalproject.presentation.ui.screen.addGuide.guideScreen

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.finalproject.presentation.model.addGuide.GuideUi

@Composable
fun GuideScreen(
    viewModel: GuideViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    if (state.guide == null && !state.isLoading && state.errorMessage == null) {
        Text("Guide not found", color = MaterialTheme.colorScheme.error)
    }

    state.guide?.let { guide ->
        GuideContent(
            guide = guide,
            onUpdateDescription = {
                viewModel.obtainEvent(GuideEvent.OnDescriptionChanged(it))
            },
            onUploadImages = { uris ->
                viewModel.obtainEvent(GuideEvent.OnUserImagesChanged(uris))
            }
        )
    }
}
@Composable
fun GuideContent(
    guide: GuideUi,
    onUpdateDescription: (String) -> Unit,
    onUploadImages: (List<Uri>) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        // Background Image
        AsyncImage(
            model = guide.data.imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(200.dp),
            contentScale = ContentScale.Crop
        )

        // Title
        Text(
            text = "${guide.location} Guide",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // Editable Description
        OutlinedTextField(
            value = guide.data.description.orEmpty(),
            onValueChange = onUpdateDescription,
            label = { Text("Add your guide description") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        // Image Upload Section
        Text("Your Photos", modifier = Modifier.padding(start = 16.dp, top = 16.dp))
        LazyRow(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(guide.data.userImages.orEmpty()) { url ->
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            item {
                IconButton(onClick = {
                    // TODO: Launch image picker
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Image")
                }
            }
        }
    }
}
