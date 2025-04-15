package com.gvituskins.utilityly.presentation.screens.main.utilities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.R
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.buttons.segmented.EndSegmentedButton
import com.gvituskins.utilityly.presentation.components.buttons.segmented.StartSegmentedButton
import com.gvituskins.utilityly.presentation.components.containers.UlyScaffold
import com.gvituskins.utilityly.presentation.components.navBar.UlyBottomNavigationBar
import com.gvituskins.utilityly.presentation.components.topAppBars.UlyDefaultTopAppBar
import com.gvituskins.utilityly.presentation.screens.main.utilities.calendar.UtilitiesCalendarScreen
import com.gvituskins.utilityly.presentation.screens.main.utilities.grid.UtilitiesGridScreen
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import kotlinx.coroutines.launch

enum class UtilitiesPagerScreens {
    CALENDAR, GRID
}

@Composable
fun UtilitiesScreen(
    navigateToAddUtility: () -> Unit,
    navigateToUtilityDetails: (Int) -> Unit,
    navigateToEditUtility: (Int) -> Unit,
    viewModel: UtilitiesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState {
        UtilitiesPagerScreens.entries.size
    }

    UlyScaffold(
        topBar = {
            UlyDefaultTopAppBar(
                title = stringResource(R.string.nav_utilities),
                actions = {
                    IconButton(onClick = navigateToAddUtility) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add new utility"
                        )
                    }
                }
            )
        },
        bottomBar = { UlyBottomNavigationBar() },
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
        ) {
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = UlyTheme.spacing.mediumSmall,
                        vertical = UlyTheme.spacing.xSmall
                    )
            ) {
                StartSegmentedButton(
                    selected = pagerState.currentPage == UtilitiesPagerScreens.CALENDAR.ordinal,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(UtilitiesPagerScreens.CALENDAR.ordinal)
                        }
                    },
                    text = "Calendar",
                    modifier = Modifier.weight(1f)
                )

                EndSegmentedButton(
                    selected = pagerState.currentPage == UtilitiesPagerScreens.GRID.ordinal,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(UtilitiesPagerScreens.GRID.ordinal)
                        }
                    },
                    text = "Grid",
                    modifier = Modifier.weight(1f),
                )
            }

            VerticalSpacer(UlyTheme.spacing.small)

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) { page ->
                when (UtilitiesPagerScreens.entries[page]) {
                    UtilitiesPagerScreens.CALENDAR -> {
                        UtilitiesCalendarScreen(
                            onUtilityClicked = navigateToUtilityDetails,
                            onEditClicked = navigateToEditUtility
                        )
                    }
                    UtilitiesPagerScreens.GRID -> {
                        UtilitiesGridScreen(
                            onUtilityClicked = navigateToUtilityDetails,
                            onEditClicked = navigateToEditUtility
                        )
                    }
                }
            }
        }
    }
}
