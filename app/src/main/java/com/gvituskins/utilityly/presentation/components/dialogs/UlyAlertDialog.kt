package com.gvituskins.utilityly.presentation.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@Composable
fun UlyAlertDialog(
    titleText: String,
    onDismissRequest: () -> Unit,
    confirmText: String,
    onConfirmClicked: () -> Unit,
    dismissText: String,
    onDismissClicked: () -> Unit = onDismissRequest,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = onConfirmClicked) {
                Text(text = confirmText)
            }
        },
        dismissButton = {
            Button(onClick = onDismissClicked) {
                Text(text = dismissText)
            }
        },
        title = {
            Text(text = titleText)
        },
        shape = UlyTheme.shapes.medium
    )
}