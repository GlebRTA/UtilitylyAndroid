package com.gvituskins.utilityly.presentation.core.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@SuppressLint("ComposableNaming")
@Composable
@NonRestartableComposable
fun <T> Flow<T>.collectAsOneTimeEvent(
    key1: Any? = null,
    key2: Any? = null,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    onOneTimeEvent: suspend CoroutineScope.(T) -> Unit,
) {
    LaunchedEffect(lifecycleOwner, key1, key2, this) {
        lifecycleOwner.repeatOnLifecycle(minActiveState) {
            withContext(Dispatchers.Main.immediate) {
                collect { oneTimeEvent -> onOneTimeEvent(oneTimeEvent) }
            }
        }
    }
}
