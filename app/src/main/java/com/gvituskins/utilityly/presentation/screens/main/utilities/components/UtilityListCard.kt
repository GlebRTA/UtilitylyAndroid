package com.gvituskins.utilityly.presentation.screens.main.utilities.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gvituskins.utilityly.presentation.components.HorizontalSpacer
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.buttons.UlyOutlinedButton
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import com.gvituskins.utilityly.presentation.theme.UtilitylyTheme

@Composable
fun UtilityListCard(
    name: String,
    amount: String,
    isPaid: Boolean,
    onActionClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = UlyTheme.shapes.small,
        border = BorderStroke(
            width = UlyTheme.spacing.border,
            color = UlyTheme.colors.outline
        ),
        colors = CardDefaults.cardColors(
            containerColor = UlyTheme.colors.background
        )
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = UlyTheme.spacing.medium,
                    vertical = UlyTheme.spacing.mediumSmall
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.WaterDrop,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                    HorizontalSpacer(UlyTheme.spacing.small)
                    Text(
                        text = name,
                        style = UlyTheme.typography.titleLarge
                    )
                }

                Text(
                    text = amount,
                    style = UlyTheme.typography.titleLarge
                )
            }

            VerticalSpacer(UlyTheme.spacing.large)

            Box(modifier = Modifier.fillMaxWidth()) {
                UlyOutlinedButton(
                    onClick = { onActionClicked() },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(180.dp)
                ) {
                    Text(
                        text = "Edit",
                        style = UlyTheme.typography.titleMedium
                    )
                }

                CheckedIcon(
                    isChecked = isPaid,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    }
}

@Composable
@Preview
private fun UtilityListCardPreview() {
    UtilitylyTheme {
        UtilityListCard(
            name = "Water",
            amount = "10.00$",
            isPaid = false,
            onActionClicked = {},
        )
    }
}
