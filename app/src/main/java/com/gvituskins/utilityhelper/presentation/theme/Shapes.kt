package com.gvituskins.utilityhelper.presentation.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class UhShapes(
    val none: CornerBasedShape = RoundedCornerShape(0.dp),
    val xSmall: CornerBasedShape = RoundedCornerShape(4.dp),
    val small: CornerBasedShape = RoundedCornerShape(8.dp),
    val medium: CornerBasedShape = RoundedCornerShape(12.dp),
    val large: CornerBasedShape = RoundedCornerShape(16.dp),
    val xLarge: CornerBasedShape = RoundedCornerShape(28.dp),
    val circle: CornerBasedShape = RoundedCornerShape(50)
)

val LocalShapes = staticCompositionLocalOf<UhShapes> {
    error("No shapes provided!")
}