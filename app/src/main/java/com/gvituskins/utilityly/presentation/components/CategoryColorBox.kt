package com.gvituskins.utilityly.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@Composable
fun CategoryColorBox(
    size: Dp,
    color: Color,
    modifier: Modifier = Modifier,
    shape: Shape = UlyTheme.shapes.small,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(color)
            .border(
                width = UlyTheme.spacing.border,
                color = UlyTheme.colors.outline,
                shape = shape
            )
    )
}