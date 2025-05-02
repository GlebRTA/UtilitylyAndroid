package com.gvituskins.utilityly.presentation.screens.main.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gvituskins.utilityly.R
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.buttons.segmented.CenterSegmentedButton
import com.gvituskins.utilityly.presentation.components.buttons.segmented.EndSegmentedButton
import com.gvituskins.utilityly.presentation.components.buttons.segmented.StartSegmentedButton
import com.gvituskins.utilityly.presentation.components.containers.UlyScaffold
import com.gvituskins.utilityly.presentation.components.navBar.UlyBottomNavigationBar
import com.gvituskins.utilityly.presentation.components.topAppBars.UlyDefaultTopAppBar
import com.gvituskins.utilityly.presentation.screens.main.statistics.statCategory.StatCategoryScreen
import com.gvituskins.utilityly.presentation.screens.main.statistics.statTable.StatTableScreen
import com.gvituskins.utilityly.presentation.screens.main.statistics.statYears.StatYearsScreen
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import kotlinx.coroutines.launch

enum class StatisticsPagerScreens {
    CATEGORY, YEARS, TABLE
}

@Composable
fun StatisticsScreen(
    navigateToTable: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState {
        StatisticsPagerScreens.entries.size
    }

    UlyScaffold(
        topBar = { UlyDefaultTopAppBar(title = stringResource(R.string.nav_statistics)) },
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
                    selected = pagerState.currentPage == StatisticsPagerScreens.CATEGORY.ordinal,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(StatisticsPagerScreens.CATEGORY.ordinal)
                        }
                    },
                    text = "By Category",
                    modifier = Modifier.weight(1f)
                )

                CenterSegmentedButton(
                    selected = pagerState.currentPage == StatisticsPagerScreens.YEARS.ordinal,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(StatisticsPagerScreens.YEARS.ordinal)
                        }
                    },
                    text = "Years",
                    modifier = Modifier.weight(1f),
                )

                EndSegmentedButton(
                    selected = pagerState.currentPage == StatisticsPagerScreens.TABLE.ordinal,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(StatisticsPagerScreens.TABLE.ordinal)
                        }
                    },
                    text = "Table",
                    modifier = Modifier.weight(1f),
                )
            }

            VerticalSpacer(UlyTheme.spacing.small)

            HorizontalPager(state = pagerState) { page ->
                when (StatisticsPagerScreens.entries[page]) {
                    StatisticsPagerScreens.CATEGORY -> {
                        StatCategoryScreen()
                    }
                    StatisticsPagerScreens.YEARS -> {
                        StatYearsScreen()
                    }
                    StatisticsPagerScreens.TABLE -> {
                        StatTableScreen(
                            navigateToTable = navigateToTable
                        )
                    }
                }
            }
        }
    }

}
