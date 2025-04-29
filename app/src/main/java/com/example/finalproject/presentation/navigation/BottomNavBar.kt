package com.example.finalproject.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalproject.R
import com.example.finalproject.presentation.ui.theme.HorizontalDividerColor
import com.example.finalproject.presentation.ui.theme.Orange
import com.example.finalproject.presentation.ui.theme.SelectedBottomIcon
import com.example.finalproject.presentation.ui.theme.UnselectedBottomIcon
import com.example.finalproject.presentation.ui.theme.White

@Composable
fun BottomNavBar(navController: NavController){

        val iconHome = ImageVector.vectorResource(id = R.drawable.homefilled)
        val iconBed = ImageVector.vectorResource(id = R.drawable.bed_unselected)
        val iconBuild = Icons.Default.Build
        val iconProfile = ImageVector.vectorResource(id = R.drawable.profile_unselected)

        val selected = remember {
            mutableStateOf(iconHome)
        }

        var showButtons by remember { mutableStateOf(false) } // for FAB buttons displayed

        var rotated by remember { mutableStateOf(false) }
        val rotationAngle by animateFloatAsState(
            targetValue = if (rotated) 45f else 0f, // Rotate 180° on click
            animationSpec = tween(durationMillis = 300),
            label = "rotationAnimation"
        )




        Scaffold(
            bottomBar = {
                Column {
                    HorizontalDivider(color = HorizontalDividerColor, thickness = 1.dp)
                    BottomAppBar(
                        containerColor = White,
                        tonalElevation = 8.dp,
                        modifier = Modifier.height(60.dp)
                    ) {
                        //Home icon in navbar
                        IconButton(
                            onClick = {
                                selected.value = iconHome
                                navController.navigate(HomeScreenDestination) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                }
                            }, modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                iconHome,
                                contentDescription = null,
                                modifier = Modifier.size(28.dp),
                                tint = if (selected.value == iconHome) SelectedBottomIcon else UnselectedBottomIcon
                            )
                        }


                        //Hotels icon in navbar
                        IconButton(
                            onClick = {
                                selected.value = iconBed
                                navController.navigate(BookHotelScreenDestination) {
                                    popUpTo(navController.graph.startDestinationId)
                                }
                            }, modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                iconBed,
                                contentDescription = null,
                                modifier = Modifier.size(28.dp),
                                tint = if (selected.value == iconBed) SelectedBottomIcon else UnselectedBottomIcon
                            )
                        }

                        //fab
                        Box(
                            modifier = Modifier
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            FloatingActionButton(
                                onClick = {
                                    rotated = !rotated
                                    showButtons = rotated

                                },
                                containerColor = Orange,
                                shape = RoundedCornerShape(50.dp),
                                modifier = Modifier
                                    .height(42.dp)
                                    .width(42.dp)
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .rotate(rotationAngle)
                                        .size(28.dp)
                                )
                            }
                        }


                        //?
                        IconButton(
                            onClick = {
                                selected.value = iconBuild
                                navController.navigate(SettingsScreenDestination) {
                                    popUpTo(navController.graph.startDestinationId)
                                }
                            }, modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                iconBuild,
                                contentDescription = null,
                                modifier = Modifier.size(28.dp),
                                tint = if (selected.value == iconBuild) SelectedBottomIcon else UnselectedBottomIcon
                            )
                        }


                        //Profile icon navbar
                        IconButton(
                            onClick = {
                                selected.value = iconProfile
                                navController.navigate(ProfileScreenDestination) {
                                    popUpTo(navController.graph.startDestinationId)
                                }
                            }, modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                iconProfile,
                                contentDescription = null,
                                modifier = Modifier.size(28.dp),
                                tint = if (selected.value == iconProfile) SelectedBottomIcon else UnselectedBottomIcon
                            )
                        }

                    }
                }
            }
        ) { paddingValues ->

            Box(
                modifier = Modifier.padding(paddingValues)
            ) {  }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp) // to stay above BottomAppBar
            ) {
                if (showButtons) {
                    // Semi-transparent background
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x005C5C5C).copy(alpha = 0.5f))
                            .clickable {
                                rotated = false
                                showButtons = false
                            }
                    )
                }
                Row (
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 20.dp)
                ){
                    AnimatedVisibility(
                        visible = showButtons,
                        enter =  scaleIn(),
                        exit = scaleOut()
                    ) {
                        FloatingActionButton(
                            modifier = Modifier.width(100.dp).height(70.dp),
                            shape = RoundedCornerShape(50.dp),
                            onClick = {navController.navigate(AddTripScreenDestination)  },
                            containerColor = White
                        ) {
                            Text(text = "Trip plan", textAlign = TextAlign.Center)
                        }
                    }


                    AnimatedVisibility(
                        visible = showButtons,
                        enter = scaleIn(animationSpec = tween(delayMillis = 50)),
                        exit = scaleOut()
                    ) {
                        Row {
                            Spacer(Modifier.width(10.dp))
                            FloatingActionButton(
                                modifier = Modifier.width(100.dp).height(70.dp),
                                shape = RoundedCornerShape(50.dp),
                                onClick = {},
                                containerColor = White
                            ) {
                                Text(text = "Guide", textAlign = TextAlign.Center)
                            }
                        }
                    }

                }


            }
        }



}