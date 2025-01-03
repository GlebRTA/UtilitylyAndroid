package com.gvituskins.utilityhelper.presentation.views.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BaseOpenableSection(
    isOpened: Boolean,
    visibleContent: @Composable () -> Unit,
    openableContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        visibleContent()
        AnimatedVisibility(
            visible = isOpened
        ) {
            openableContent()
        }
    }
}
