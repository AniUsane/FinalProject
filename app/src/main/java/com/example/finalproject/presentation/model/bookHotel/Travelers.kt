package com.example.finalproject.presentation.model.bookHotel

import java.io.Serializable

data class Travelers(
    val rooms: Int,
    val adults: Int,
    val children: Int
): Serializable