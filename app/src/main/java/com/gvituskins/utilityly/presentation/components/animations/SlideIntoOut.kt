package com.gvituskins.utilityly.presentation.components.animations

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith

fun AnimatedContentTransitionScope<Int>.slideIntoOut(
    intoDuration: Int = 500,
    outDuration: Int = 500,
): ContentTransform {
    return if (initialState > targetState) {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Up,
            animationSpec = tween(durationMillis = intoDuration)
        ) togetherWith slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Up,
            animationSpec = tween(durationMillis = outDuration)
        )
    } else {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Down,
            animationSpec = tween(durationMillis = intoDuration)
        ) togetherWith slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Down,
            animationSpec = tween(durationMillis = outDuration)
        )
    }
}
