package com.gvituskins.utilityly.presentation.screens.main.utilities.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gvituskins.utilityly.presentation.components.takeIf
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import com.gvituskins.utilityly.presentation.theme.UtilitylyTheme
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import java.time.LocalDate

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    isToday: Boolean,
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
        val trueColor = if (isToday) Color.Red else UlyTheme.colors.onBackground
        Text(
            text = day.date.dayOfMonth.toString(),
            color = if (day.position == DayPosition.MonthDate) trueColor else trueColor.copy(alpha = 0.4f)
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

@Composable
@Preview
private fun DayPreview() {
    UtilitylyTheme {
        Day(
            day = CalendarDay(LocalDate.now(), DayPosition.InDate),
            isSelected = true,
            isToday = true,
            onClick = {  },
            utilities = listOf()
        )
    }
}