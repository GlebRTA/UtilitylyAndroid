package com.gvituskins.utilityly.presentation.views.sections

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@Composable
fun SettingsOpenableSection(
    isOpened: Boolean,
    onOpen: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val openRotation by animateFloatAsState(
        targetValue = if (isOpened) 90f else 0f,
        label = "Open settings animation"
    )

    Column {
        BaseOpenableSection(
            isOpened = isOpened,
            visibleContent = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = UlyTheme.spacing.mediumSmall),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Theme")

                    IconButton(onClick = { onOpen(!isOpened) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Open setting section",
                            modifier = Modifier.graphicsLayer {
                                rotationZ = openRotation
                            }
                        )
                    }
                }
            },
            openableContent = { content() },
            modifier = modifier
        )
        HorizontalDivider()
    }
}
