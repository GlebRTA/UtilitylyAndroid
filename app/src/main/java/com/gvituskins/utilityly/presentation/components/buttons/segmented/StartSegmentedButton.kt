package com.gvituskins.utilityly.presentation.components.buttons.segmented

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SingleChoiceSegmentedButtonRowScope.StartSegmentedButton(
    selected: Boolean,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    corner: Dp = 8.dp
) {
    SegmentedButton(
        selected = selected,
        onClick = onClick,
        shape = RoundedCornerShape(topStart = corner, bottomStart = corner),
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Composable
fun SingleChoiceSegmentedButtonRowScope.EndSegmentedButton(
    selected: Boolean,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    corner: Dp = 8.dp
) {
    SegmentedButton(
        selected = selected,
        onClick = onClick,
        shape = RoundedCornerShape(topEnd = corner, bottomEnd = corner),
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Composable
fun SingleChoiceSegmentedButtonRowScope.CenterSegmentedButton(
    selected: Boolean,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    SegmentedButton(
        selected = selected,
        onClick = onClick,
        shape = RectangleShape,
        modifier = modifier
    ) {
        Text(text = text)
    }
}
