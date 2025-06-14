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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.presentation.core.utils.UiConstants
import com.gvituskins.utilityly.presentation.core.utils.roundToStr
import com.gvituskins.utilityly.presentation.screens.main.utilities.grid.components.UtilityGridCard
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import java.time.format.DateTimeFormatter

@Composable
fun UtilitiesGridScreen(
    onUtilityClicked: (Int) -> Unit,
    onEditClicked: (Int) -> Unit,
    viewModel: UtilitiesGridViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.updateMonth()
    }

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

                    Text(text = uiState.currentMont.format(DateTimeFormatter.ofPattern("MMMM, yyyy")))

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
                    paid = uiState.paid.roundToStr(),
                    unpaid = uiState.unpaid.roundToStr(),
                    total = uiState.total.roundToStr()
                )
            }

            items(items = uiState.utilities, key = { it.id }) { utility ->
                UtilityGridCard(
                    id = utility.id,
                    amount = "${UiConstants.CURRENCY_SIGN}${utility.amount}",
                    category = utility.category.name,
                    isPaid = utility.paidStatus.isPaid,
                    color = utility.category.color,
                    onEditClicked = { onEditClicked(utility.id) },
                    modifier = Modifier
                        .padding(UlyTheme.spacing.small)
                        .clickable { onUtilityClicked(utility.id) }
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
            text = "Paid: ${UiConstants.CURRENCY_SIGN}$paid",
            style = UlyTheme.typography.titleMedium
        )

        Text(
            text = "Unpaid: ${UiConstants.CURRENCY_SIGN}$unpaid",
            style = UlyTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = UlyTheme.spacing.small)
        )

        Text(
            text = "Total: ${UiConstants.CURRENCY_SIGN}$total",
            style = UlyTheme.typography.titleMedium
        )
    }
}
