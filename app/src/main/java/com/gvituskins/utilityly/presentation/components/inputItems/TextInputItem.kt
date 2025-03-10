package com.gvituskins.utilityly.presentation.components.inputItems

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@Composable
fun TextInputItem(
    title: String?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BaseInputItem(
        top = {
            title?.let {
                Text(
                    text = it,
                    style = UlyTheme.typography.titleLarge
                )

                VerticalSpacer(UlyTheme.spacing.small)
            }
        },
        bottom = { content() },
        modifier = modifier
    )
}
