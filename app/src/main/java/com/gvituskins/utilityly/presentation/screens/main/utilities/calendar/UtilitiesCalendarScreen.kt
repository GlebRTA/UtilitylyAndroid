package com.gvituskins.utilityly.presentation.screens.main.utilities.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.presentation.components.takeIf
import com.gvituskins.utilityly.presentation.screens.main.utilities.components.UtilityListCard
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.yearMonth
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun UtilitiesCalendarScreen(
    onUtilityClicked: (Int) -> Unit,
    onEditClicked: (Int) -> Unit,
    viewModel: UtilitiesCalendarViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    val currentYearMonth = remember { YearMonth.now() }

    Column(modifier = Modifier.fillMaxSize()) {
        val currentMonth = remember { YearMonth.now() }
        val startMonth = remember { currentMonth.minusMonths(100) }
        val endMonth = remember { currentMonth.plusMonths(100) }
        val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = firstDayOfWeek
        )

        LaunchedEffect(state.firstVisibleMonth, state.isScrollInProgress) {
            if (!state.isScrollInProgress) {
                viewModel.updateMonth(state.firstVisibleMonth)
            }
        }

        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                Day(
                    day = day,
                    isSelected = day == uiState.selectedDay,
                    onClick = { viewModel.updateSelectedDay(day) },
                    utilities = uiState.cachedMonthUtilities
                        .filter { utility -> day.date == utility.dueDate }
                        .map { it.paidStatus.isPaid to it.category.color }
                )
            },
            monthHeader = { month ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(UlyTheme.colors.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    if (month.yearMonth != currentMonth) {
                        Text(
                            text = "Today",
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = UlyTheme.spacing.mediumSmall)
                                .border(1.dp, UlyTheme.colors.onBackground, UlyTheme.shapes.small)
                                .clickable {
                                    scope.launch {
                                        state.animateScrollToMonth(currentYearMonth)
                                    }
                                }
                                .padding(vertical = 2.dp, horizontal = 8.dp)
                        )
                    }

                    Text(
                        text = month.yearMonth.format(DateTimeFormatter.ofPattern("MMMM, yyyy")),
                        modifier = Modifier.padding(UlyTheme.spacing.xSmall),
                        style = UlyTheme.typography.titleLarge,
                    )
                }
                HorizontalDivider()
            },
            monthFooter = { month ->
                HorizontalDivider()
                uiState.selectedDay?.let { selectedDay ->
                    if (selectedDay.date.yearMonth == month.yearMonth) {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(vertical = UlyTheme.spacing.mediumSmall),
                            verticalArrangement = Arrangement.spacedBy(UlyTheme.spacing.mediumSmall),
                        ) {
                            items(items = uiState.dayUtilities, key = { it.id }) { utility ->
                                UtilityListCard(
                                    name = utility.category.name,
                                    amount = utility.amount.toString(),
                                    isPaid = utility.paidStatus.isPaid,
                                    color = utility.category.color,
                                    onActionClicked = { onEditClicked(utility.id) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = UlyTheme.spacing.small)
                                        .clickable { onUtilityClicked(utility.id) }
                                        .animateItem()
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit,
    utilities: List<Pair<Boolean, Color>>
) {
    Column(
        modifier = Modifier
            .size(56.dp)
            .padding(UlyTheme.spacing.small)
            .takeIf(isSelected) {
                Modifier.border(
                    width = UlyTheme.spacing.border,
                    color = UlyTheme.colors.outline,
                    shape = UlyTheme.shapes.circle
                )
            }
            .clip(UlyTheme.shapes.circle)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = if (day.position == DayPosition.MonthDate) {
                UlyTheme.colors.onBackground
            } else {
                UlyTheme.colors.onBackground.copy(alpha = 0.4f)
            }
        )

        if (utilities.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                utilities.forEach { pair ->
                    val isPaid = pair.first
                    val categoryColor = pair.second

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(UlyTheme.shapes.circle)
                            .background(if (isPaid) Color.Green else Color.Red)
                            .border(
                                width = 2.dp,
                                color = categoryColor,
                                shape = CircleShape
                            ),
                    )
                }
            }
        }
    }
}

