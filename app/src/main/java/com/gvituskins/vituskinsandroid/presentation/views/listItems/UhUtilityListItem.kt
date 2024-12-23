package com.gvituskins.vituskinsandroid.presentation.views.listItems

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun UhUtilityListItem(
    primaryText: String,
    descriptionText: String?,
    onStartBtnClicked: () -> Unit,
    onEndBtnClicked: () -> Unit,
    startBtnText: String = "Details",
    endBtnText: String = "Paid",
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = primaryText,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            descriptionText?.let { description ->
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Row {
            FilledTonalButton(
                onClick = onStartBtnClicked
            ) {
                Text(text = startBtnText)
            }

            Spacer(modifier = Modifier.width(8.dp))

            FilledTonalButton(
                onClick = onEndBtnClicked
            ) {
                Text(text = endBtnText)
            }
        }
    }
}
