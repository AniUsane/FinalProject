package com.example.finalproject.presentation.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController

inline fun <reified T> NavController.setNavigationResult(key: String, result: T) {
    previousBackStackEntry?.savedStateHandle?.set(key, result)
}

inline fun <reified T> NavController.getNavigationResult(key: String): SavedStateHandle.() -> T? {
    return { get<T>(key) }
}