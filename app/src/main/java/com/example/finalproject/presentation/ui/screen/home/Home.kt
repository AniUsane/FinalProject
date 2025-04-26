package com.example.finalproject.presentation.ui.screen.home
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.finalproject.R
import com.example.finalproject.presentation.mapper.toPresentation
import com.example.finalproject.presentation.ui.screen.home.state.PopularDestinationState
import com.example.finalproject.presentation.ui.screen.home.state.UserGuideState
import com.example.finalproject.presentation.ui.screen.home.state.WeekendTripState
import com.example.finalproject.presentation.ui.theme.White

@Composable
fun Home(
    viewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchUserGuides()
    }


    Column(
        modifier = Modifier.background(color = White)
            .verticalScroll(rememberScrollState())
    ) {



    TopImageCard(
        painter = painterResource(R.drawable.img),
        contentDescription = "Card Image",
        title = "Plan your next adventure",
        buttonText = "Create new trip plan"
    )

        Spacer(modifier = Modifier.height(35.dp))
        Text("Featured guides from users", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp, bottom = 8.dp), fontSize = 25.sp)


    val userGuideState by viewModel.userGuidesState.collectAsStateWithLifecycle()
    val weekendTripState by viewModel.weekendTripState.collectAsStateWithLifecycle()
    val popularDestinationState by viewModel.popularDestinationState.collectAsStateWithLifecycle()
    Log.d("Home", "State: $userGuideState")
    when (userGuideState) {
        is UserGuideState.Success -> {
            Log.d("Home", "State: $userGuideState")
            LazyRow(
                modifier = Modifier.padding(start = 3.dp, end = 3.dp)
            ) {
                items((userGuideState as UserGuideState.Success).usersGuides) { userGuide ->
                    UserGuideItem(userGuide.toPresentation())
                }
            }
        }

        is UserGuideState.IsLoading -> {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally). padding(top = 100.dp, bottom = 100.dp))
        }

        is UserGuideState.Error -> {

        }


        UserGuideState.Idle -> {}
    }

        Spacer(modifier = Modifier.height(50.dp))
        Text("Weekend trips", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp, bottom = 8.dp), fontSize = 25.sp)

        when(weekendTripState) {
            is WeekendTripState.Success -> {
                LazyRow(
                    modifier = Modifier.padding(start = 3.dp, end = 3.dp)
                ) {
                    items((weekendTripState as WeekendTripState.Success).weekendTrips) { weekendTrip ->
                        TripItem(weekendTrip)
                    }
                }
            }

            is WeekendTripState.IsLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally). padding(top = 50.dp, bottom = 50.dp))
            }

            is WeekendTripState.Error -> {

            }


            WeekendTripState.Idle -> {}
        }

        Spacer(modifier = Modifier.height(35.dp))
        Text("Popular destinations", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp, bottom = 8.dp), fontSize = 25.sp)

        when(popularDestinationState) {
            is PopularDestinationState.Success -> {
                LazyRow(
                    modifier = Modifier.padding(start = 3.dp, end = 3.dp)
                ) {
                    items((popularDestinationState as PopularDestinationState.Success).popularDestinations) { popularDestination ->
                        TripItem(popularDestination)
                    }
                }
            }

            is PopularDestinationState.IsLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally). padding(top = 50.dp, bottom = 50.dp))
            }

            is PopularDestinationState.Error -> {

            }


            PopularDestinationState.Idle -> {}
        }

}





}