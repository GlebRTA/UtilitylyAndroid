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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.presentation.screens.main.utilities.components.UtilityGridCard
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@Composable
fun UtilitiesGridScreen(
    viewModel: UtilitiesGridViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
        ) {
            item(span = { GridItemSpan(2) }) {
                UtilitiesInfoHeader(
                    paid = "200",
                    unpaid = "110",
                    total = "310"
                )
            }

            items(13) {
                UtilityGridCard(
                    modifier = Modifier
                        .padding(UlyTheme.spacing.small)
                        .clickable { }
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
