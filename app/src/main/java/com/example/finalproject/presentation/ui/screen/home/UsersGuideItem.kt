package com.example.finalproject.presentation.ui.screen.home
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.finalproject.presentation.model.guide.UserGuideUi
import com.example.finalproject.presentation.ui.theme.PostLightGreyColor

@Composable
fun UserGuideItem(userGuideUi: UserGuideUi) {
    Column {
        Card (
            modifier = Modifier
                .padding(5.dp)
                .width(220.dp)
                .height(150.dp),
            shape = RoundedCornerShape(12.dp)
        ){
        AsyncImage(
            model = userGuideUi.imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = "image",
            modifier = Modifier
                .fillMaxSize()

        )
        }

        Text(text = userGuideUi.title, modifier = Modifier
            .width(220.dp)
            .padding(start = 5.dp),
            fontSize = 15.sp,
            maxLines = 1

        )
        Text(text = userGuideUi.summary,
            maxLines = 2, modifier = Modifier
                .width(220.dp)
                .padding(start = 5.dp),
            fontSize = 14.sp,
            lineHeight = 18.sp,
            color = PostLightGreyColor
        )

        Row (
            modifier = Modifier
                .padding(start = 5.dp, top = 8.dp)
        ){
            AsyncImage(
                model = userGuideUi.userImageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = "user image",
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(50)),

            )

            Text(text = userGuideUi.username, modifier = Modifier
                .padding(start = 5.dp),
                fontSize = 11.sp,
                color = PostLightGreyColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MaterialTheme {
//        UserGuideItem()
    }
}