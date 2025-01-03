package com.gvituskins.utilityhelper.presentation.views.listItems

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gvituskins.utilityhelper.presentation.theme.UhTheme

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
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = primaryText,
                style = UhTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            descriptionText?.let { description ->
                Text(
                    text = description,
                    style = UhTheme.typography.bodyMedium
                )
            }
        }

        Row {
            Button(
                onClick = onStartBtnClicked
            ) {
                Text(text = startBtnText)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = onEndBtnClicked
            ) {
                Text(text = endBtnText)
            }
        }
    }
}
