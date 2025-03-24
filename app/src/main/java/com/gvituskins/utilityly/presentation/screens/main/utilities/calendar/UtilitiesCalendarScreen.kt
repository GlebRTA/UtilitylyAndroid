package com.gvituskins.utilityly.presentation.screens.main.utilities.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.presentation.components.takeIf
import com.gvituskins.utilityly.presentation.core.utils.debugLog
import com.gvituskins.utilityly.presentation.screens.main.utilities.components.UtilityListCard
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun UtilitiesCalendarScreen(
    viewModel: UtilitiesCalendarViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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

        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                Day(
                    day = day,
                    isSelected = day == uiState.selectedDay,
                    onClick = { viewModel.updateSelectedDay(day) }
                )
            },
            monthHeader = { month ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = month.yearMonth.format(DateTimeFormatter.ofPattern("MMMM, yyyy")),
                        modifier = Modifier.padding(UlyTheme.spacing.xSmall),
                        style = UlyTheme.typography.titleLarge,
                    )
                }
                HorizontalDivider()
            },
            monthFooter = { month ->
                debugLog(text = "month = ${month}")
                HorizontalDivider()
                uiState.selectedDay?.let { selectedDay ->
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(vertical = UlyTheme.spacing.mediumSmall),
                        verticalArrangement = Arrangement.spacedBy(UlyTheme.spacing.mediumSmall),
                    ) {
                        items(3) {
                            UtilityListCard(
                                name = "Water paid",
                                amount = "10.00$",
                                isPaid = true,
                                onActionClicked = {},
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = UlyTheme.spacing.small)
                                    .clickable { }
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .padding(UlyTheme.spacing.small)
            .takeIf(isSelected) {
                Modifier.border(
                    width = UlyTheme.spacing.border,
                    color = UlyTheme.colors.onBackground,
                    shape = UlyTheme.shapes.circle
                )
            }
            .clip(UlyTheme.shapes.circle)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = if (day.position == DayPosition.MonthDate) UlyTheme.colors.onBackground else UlyTheme.colors.onBackground.copy(
                alpha = 0.4f
            )
        )
    }
}
