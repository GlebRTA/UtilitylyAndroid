package com.gvituskins.utilityly.presentation.screens.main.utilities.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import com.gvituskins.utilityly.presentation.theme.UtilitylyTheme

@Composable
fun UtilityGridCard(
    modifier: Modifier = Modifier,
    innerPaddings: PaddingValues = PaddingValues(UlyTheme.spacing.mediumSmall)
) {
    Card(
        modifier = modifier,
        shape = UlyTheme.shapes.small,
        border = BorderStroke(
            width = UlyTheme.spacing.border,
            color = UlyTheme.colors.onBackground
        ),
        colors = CardDefaults.cardColors(
            containerColor = UlyTheme.colors.background
        )
    ) {
        Column(
            modifier = Modifier.padding(innerPaddings)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.WaterDrop,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )

                CheckedIcon(
                    isChecked = false,
                    modifier = Modifier.size(28.dp)
                )
            }

            VerticalSpacer(UlyTheme.spacing.medium)

            Text(
                text = "10.00$",
                style = UlyTheme.typography.titleLarge
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Water")

                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit utility")
                }
            }
        }
    }
}

@Composable
@Preview
private fun UtilityGridCardPreview() {
    UtilitylyTheme {
        UtilityGridCard()
    }
}