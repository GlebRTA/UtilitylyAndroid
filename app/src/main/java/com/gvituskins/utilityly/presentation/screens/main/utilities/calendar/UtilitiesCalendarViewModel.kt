package com.gvituskins.utilityly.presentation.screens.main.utilities.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.domain.models.utilities.Utility
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UtilitiesCalendarViewModel @Inject constructor(
    private val utilityRepository: UtilityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UtilitiesCalendarState())
    val uiState = _uiState.asStateFlow()

    fun updateMonth(newMonth: CalendarMonth) {
        viewModelScope.launch {
            updateCachedUtilities(newMonth)
            updateDay()
        }
    }

    private suspend fun updateCachedUtilities(month: CalendarMonth) = coroutineScope {
        val currentMonth = month.yearMonth
        val prevMonth = currentMonth.minusMonths(1)
        val nextMonth = currentMonth.plusMonths(1)

        val monthUtilities = listOf(
            async { utilityRepository.getAllUtilitiesByMonth(prevMonth.monthValue, prevMonth.year) },
            async { utilityRepository.getAllUtilitiesByMonth(currentMonth.monthValue, currentMonth.year) },
            async { utilityRepository.getAllUtilitiesByMonth(nextMonth.monthValue, nextMonth.year) }
        )
            .awaitAll()
            .flatten()

        _uiState.update { currentUiState ->
            currentUiState.copy(
                cachedMonthUtilities = monthUtilities
            )
        }
    }

    fun updateSelectedDay(newDay: CalendarDay) {
        _uiState.update { currentUiState ->
            currentUiState.copy(selectedDay = newDay)
        }
        updateDay()
    }

    private fun updateDay() {
        val date = uiState.value.selectedDay?.date
        val day = date?.dayOfMonth
        val month = date?.monthValue
        val year = date?.year

        val utilities = if (day == null || month == null || year == null) {
            listOf()
        } else {
            uiState.value.cachedMonthUtilities.filter { utility -> utility.dueDate == date }
        }

        _uiState.update { currentUiState ->
            currentUiState.copy(
                dayUtilities = utilities
            )
        }
    }
}


data class UtilitiesCalendarState(
    val selectedDay: CalendarDay? = null,
    val dayUtilities: List<Utility> = listOf(),

    val cachedMonthUtilities: List<Utility> = listOf(),
)
