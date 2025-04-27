package com.example.finalproject.presentation.navigation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.finalproject.R
import com.example.finalproject.presentation.ui.theme.Black
import com.example.finalproject.presentation.ui.theme.LightGray
import com.example.finalproject.presentation.ui.theme.TransparentColor
import com.example.finalproject.presentation.ui.theme.White

@Composable
fun BottomNavBar(navController: NavController){

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        modifier = Modifier.height(70.dp).border(0.5.dp, LightGray),
        containerColor = White
    ) {
        NavigationBarItem(
            selected = currentRoute == HomeScreenDestination::class.qualifiedName,
            onClick = {navController.navigate(HomeScreenDestination)},
            icon = { Icon(Icons.Default.Home, contentDescription = "Home",
                tint = if(currentRoute == HomeScreenDestination::class.qualifiedName) Black else LightGray)},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Black,
                unselectedIconColor = LightGray,
                indicatorColor = TransparentColor
            )
        )
        NavigationBarItem(
            selected = currentRoute == BookHotelScreenDestination::class.qualifiedName,
            onClick = {navController.navigate(BookHotelScreenDestination)},
            icon = { Icon(Icons.Default.Hotel, contentDescription = "BookHotel",
                tint = if(currentRoute == BookHotelScreenDestination::class.qualifiedName) Black else LightGray)},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Black,
                unselectedIconColor = LightGray,
                indicatorColor = TransparentColor
            )
        )
        NavigationBarItem(
            selected = currentRoute == HomeScreenDestination::class.qualifiedName,
            onClick = {navController.navigate(HomeScreenDestination)},
            icon = { Icon(Icons.Default.Add, contentDescription = "")}
        )

        NavigationBarItem(
            selected = currentRoute == SettingsScreenDestination::class.qualifiedName,
            onClick = { navController.navigate(SettingsScreenDestination) },
            icon = { Icon(painterResource(R.drawable.price_tag), contentDescription = "Settings",
                tint = if(currentRoute == SettingsScreenDestination::class.qualifiedName) Black else LightGray)},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Black,
                unselectedIconColor = LightGray,
                indicatorColor = TransparentColor
            )
        )
        NavigationBarItem(
            selected = currentRoute == ProfileScreenDestination::class.qualifiedName,
            onClick = { navController.navigate(ProfileScreenDestination) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile",
                tint = if(currentRoute == ProfileScreenDestination::class.qualifiedName) Black else LightGray)},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Black,
                unselectedIconColor = LightGray,
                indicatorColor = TransparentColor
            )
        )
    }
}