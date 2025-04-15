package com.example.finalproject.presentation.ui.screen.home
import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.finalproject.R
import com.example.finalproject.presentation.mapper.toPresentation
import com.example.finalproject.presentation.ui.screen.home.state.UserGuideState
import com.example.tbc_final.ui.screen.home.HomeViewModel

@Composable
fun Home(
    viewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchUserGuides()
    }

    Column(
        modifier = Modifier.
        verticalScroll(rememberScrollState())
    ) {



    TopImageCard(
        painter = painterResource(R.drawable.img),
        contentDescription = "Card Image",
        title = "Plan your next adventure",
        buttonText = "Create new trip plan"
    )

        Spacer(modifier = Modifier.height(30.dp))
        Text("Featured guides from users", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp, bottom = 8.dp), fontSize = 25.sp)


    val userGuideState by viewModel.userGuidesState.collectAsStateWithLifecycle()
    Log.d("Home", "State: $userGuideState")
    when (userGuideState) {
        is UserGuideState.Success -> {
            Log.d("Home", "State: $userGuideState")
            LazyRow(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                items((userGuideState as UserGuideState.Success).usersGuides) { userGuide ->
                    UserGuideItem(userGuide.toPresentation())
                }
            }
        }

        is UserGuideState.IsLoading -> {
        CircularProgressIndicator()
        }

        is UserGuideState.Error -> {

        }


        UserGuideState.Idle -> {}
    }

        Spacer(modifier = Modifier.height(300.dp))

}





}