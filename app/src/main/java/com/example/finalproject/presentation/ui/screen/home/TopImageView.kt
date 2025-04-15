package com.example.finalproject.presentation.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject.R
import com.example.finalproject.presentation.ui.theme.Orange

@Composable
fun TopImageCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    buttonText: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RectangleShape
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val squareSize = maxWidth

            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = Modifier
                    .width(squareSize)
                    .height(squareSize),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(squareSize)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 600f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(squareSize)
                    .padding(start = 18.dp, bottom = 12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column {
                    Text(
                        title,
                        style = TextStyle(color = Color.White, fontSize = 32.sp, fontWeight = Bold),
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .padding(bottom = 10.dp)
                    )
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Orange, // background
                            contentColor = Color.White
                        ),
                    ) {

                            Text(
                                text = buttonText,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center
                            )

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TopPreview() {
    TopImageCard(
        painter = painterResource(R.drawable.img),
        contentDescription = "Card Image",
        title = "Plan your next adventure",
        buttonText = "Create new trip plan"
    )
}