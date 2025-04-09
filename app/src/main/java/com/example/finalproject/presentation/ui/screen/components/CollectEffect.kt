package com.example.finalproject.presentation.ui.screen.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> CollectEffect(flow: Flow<T>, sideEffect: suspend (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(sideEffect)
        }
    }
}