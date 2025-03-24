package com.gvituskins.utilityly.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.takeIf(condition: Boolean, modifier: @Composable Modifier.() -> Modifier): Modifier {
    return if (condition) {
        this then (modifier(Modifier))
    } else {
        this
    }
}
