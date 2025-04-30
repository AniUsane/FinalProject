package com.example.finalproject.presentation.ui.screen.home.guidepage

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.finalproject.presentation.mapper.toPresentation
import com.example.finalproject.presentation.model.guide.UserGuideUi
import com.example.finalproject.presentation.ui.screen.home.HomeViewModel
import com.example.finalproject.presentation.ui.screen.home.state.UserGuideState
import com.example.finalproject.presentation.ui.theme.PostLightGreyColor
import com.example.finalproject.presentation.ui.theme.White

@Composable
fun UserGuideDetailsScreen(guideId: Int, viewModel: HomeViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.fetchUserGuides()
    }
    val userGuideState by viewModel.userGuidesState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .background(color = White)
            .verticalScroll(rememberScrollState())
    ) {

        when (val state = userGuideState) {
            is UserGuideState.Success -> {
                val guide = state
                    .usersGuides
                    .firstOrNull { it.id == guideId }
                guide?.let { UserGuideDetails(guide.toPresentation())}
            }

            is UserGuideState.IsLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(horizontal = 100.dp, vertical = 100.dp)
                )
            }

            is UserGuideState.Error -> {

            }

            UserGuideState.Idle -> {}
        }

    }
}

@Composable
fun UserGuideDetails(userGuideUi: UserGuideUi) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp), shape = RectangleShape) {
            Box {
            AsyncImage(
                model = userGuideUi.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = "user image",
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 200f
                        )
                    )
            )
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, bottom = 12.dp),
                contentAlignment = Alignment.BottomStart
            ){
                Text(
                    text = userGuideUi.title,
                    style = TextStyle(color = Color.White, fontSize = 25.sp, fontWeight = Bold),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }
        }
        }
        Row (
            modifier = Modifier
                .padding(start = 10.dp, top = 15.dp)
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
        Text(
            text = userGuideUi.summary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            textAlign = TextAlign.Start,
            style = TextStyle(color = Color.Black),
            fontSize = 18.sp,
            lineHeight = 20.sp,
        )
        Spacer(modifier = Modifier.fillMaxWidth().height(30.dp).background(Color.LightGray))

        Text(
            text = userGuideUi.content,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            textAlign = TextAlign.Start,
            style = TextStyle(color = Color.Black),
            fontSize = 18.sp,
            lineHeight = 20.sp,
        )

        Text(
            text = userGuideUi.date,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            textAlign = TextAlign.Start,
            style = TextStyle(color = Color.LightGray),
            fontSize = 14.sp,
            lineHeight = 18.sp,
        )

    }
}

@Preview
@Composable
fun UserGuidesPreview() {
    UserGuideDetails(userGuideUi)
}

val userGuideUi = UserGuideUi(
    "Great Wall of China",
    "Ping Xiao Po",
    "Alright, if you’ve ever wanted to feel like you’re on top of the world, THIS is the place to do it! The Great Wall isn’t just a wall — it’s a fortress, a mountain path, and a history lesson all rolled into one. Stretching over 13,000 miles (that’s like running a marathon… every day, for years!), the wall was built to protect ancient China from invaders. Now, it’s one of the most famous landmarks in the world, and let me tell you — it’s totally worth the trip.",
    "Hey there, fellow adventurers! Po the Dragon Warrior here! I’ve traveled far and wide, and now I’m here to share with you the secrets of one of the most legendary places in the world: The Great Wall of China. It’s huge — like, seriously, this thing goes on and on. And no, it’s not made of noodles (but that would be awesome, right?). Let's dive in and explore! Fun Facts About the Wall (I Love Fun Facts!) The Great Wall is so long, it could stretch from New York City to London — and still have some miles to spare! It took more than 2,000 years to build! No wonder it’s so strong — it’s like the ultimate fortress. In some places, the Wall is made of stones, while in others, it’s built from brick or even earth! That’s right, earth! Don’t worry, I’m not suggesting you try to eat the wall (although I’ve been known to snack on a few things in the past...).",
    "01-04-2023",
    "https://whc.unesco.org/uploads/thumbs/site_0438_0035-1200-630-20241024162522.jpg",
    "https://imagedelivery.net/c2SKP8Bk0ZKw6UDgeeIlbw/2eff7976-2390-4631-e299-00aa68719f00/public",
    1
)