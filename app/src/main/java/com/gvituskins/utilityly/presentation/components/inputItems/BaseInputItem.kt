package com.gvituskins.utilityly.presentation.components.inputItems

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BaseInputItem(
    top: @Composable () -> Unit,
    bottom: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        top()

        bottom()
    }
}
