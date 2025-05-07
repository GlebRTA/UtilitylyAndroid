package com.gvituskins.utilityly.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CheckedIcon(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = if (isChecked) Icons.Default.Check else Icons.Default.Close,
        contentDescription = null,
        modifier = modifier,
        tint = if (isChecked) Color.Green else Color.Red
    )
}
