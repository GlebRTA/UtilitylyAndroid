package com.gvituskins.utilityly.presentation.screens.main.utilities.grid

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.presentation.screens.main.utilities.components.UtilityGridCard
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@Composable
fun UtilitiesGridScreen(
    onUtilityClicked: (Int) -> Unit,
    onEditClicked: (Int) -> Unit,
    viewModel: UtilitiesGridViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
        ) {
            item(span = { GridItemSpan(2) }) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = UlyTheme.spacing.small)
                        .padding(bottom = 12.dp)
                        .border(
                            width = UlyTheme.spacing.border,
                            color = UlyTheme.colors.outline,
                            shape = UlyTheme.shapes.small
                        )
                        .padding(UlyTheme.spacing.mediumSmall)
                ) {
                    IconButton(onClick = { viewModel.previousMonth() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }

                    Text(text = uiState.currentMont.toString())

                    IconButton(onClick = { viewModel.nextMonth() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null
                        )
                    }
                }
            }

            item(span = { GridItemSpan(2) }) {
                UtilitiesInfoHeader(
                    paid = uiState.paid.toString(),
                    unpaid = uiState.unpaid.toString(),
                    total = uiState.total.toString()
                )
            }

            items(items = uiState.utilities, key = { it.id }) { utility ->
                UtilityGridCard(
                    amount = utility.amount.toString(),
                    category = utility.category.name,
                    isPaid = utility.paidStatus.isPaid,
                    icon = Icons.Default.WaterDrop, //utility.category.iconUrl,
                    onEditClicked = { onEditClicked(utility.id) },
                    modifier = Modifier
                        .padding(UlyTheme.spacing.small)
                        .clickable { onUtilityClicked(utility.id) }
                        .animateItem()
                )
            }
        }
    }
}

@Composable
fun UtilitiesInfoHeader(
    paid: String,
    unpaid: String,
    total: String,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = UlyTheme.spacing.small)
            .border(
                width = UlyTheme.spacing.border,
                color = UlyTheme.colors.outline,
                shape = UlyTheme.shapes.small
            )
            .padding(UlyTheme.spacing.mediumSmall)
    ) {
        Text(
            text = "Paid: $$paid",
            style = UlyTheme.typography.titleMedium
        )

        Text(
            text = "Unpaid: $$unpaid",
            style = UlyTheme.typography.titleMedium
        )

        Text(
            text = "Total: $$total",
            style = UlyTheme.typography.titleMedium
        )
    }
}
