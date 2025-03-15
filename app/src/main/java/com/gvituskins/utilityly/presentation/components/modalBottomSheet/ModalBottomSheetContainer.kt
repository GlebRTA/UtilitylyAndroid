package com.gvituskins.utilityly.presentation.components.modalBottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@Composable
fun ModalBottomSheetContainer(
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = UlyTheme.spacing.mediumSmall)
    ) {
        content()
    }
}
