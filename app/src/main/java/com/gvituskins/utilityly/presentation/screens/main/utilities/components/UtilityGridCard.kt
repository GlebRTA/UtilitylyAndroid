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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gvituskins.utilityly.presentation.components.CategoryColorBox
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import com.gvituskins.utilityly.presentation.theme.UtilitylyTheme

@Composable
fun UtilityGridCard(
    amount: String,
    category: String,
    isPaid: Boolean,
    onEditClicked: () -> Unit,
    color: Color,
    modifier: Modifier = Modifier,
    innerPaddings: PaddingValues = PaddingValues(UlyTheme.spacing.mediumSmall),
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
            modifier = Modifier.padding(innerPaddings)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CategoryColorBox(
                    size = 36.dp,
                    color = color
                )

                CheckedIcon(
                    isChecked = isPaid,
                    modifier = Modifier.size(28.dp)
                )
            }

            VerticalSpacer(UlyTheme.spacing.medium)

            Text(
                text = amount,
                style = UlyTheme.typography.titleLarge
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = category)

                IconButton(onClick = onEditClicked) {
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
        UtilityGridCard(
            onEditClicked = {},
            amount = "$10:00",
            category = "Water",
            isPaid = false,
            color = Color.Magenta,
            modifier = Modifier,
        )
    }
}