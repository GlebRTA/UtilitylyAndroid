package com.gvituskins.utilityly.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class UlySpacing(
    val border: Dp = 1.dp,
    val xxSmall: Dp = 2.dp,
    val xSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val mediumSmall: Dp = 12.dp,
    val medium: Dp = 16.dp,
    val mediumLarge: Dp = 20.dp,
    val large: Dp = 24.dp,
    val xLarge: Dp = 36.dp,
    val xxLarge: Dp = 54.dp
)

val LocalSpacing = staticCompositionLocalOf<UlySpacing> {
    error("No spacing provided!")
}