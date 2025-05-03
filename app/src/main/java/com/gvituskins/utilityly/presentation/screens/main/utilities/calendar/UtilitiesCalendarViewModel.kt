package com.gvituskins.utilityly.presentation.screens.main.utilities.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.domain.models.utilities.Utility
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import dagger.hilt.android.lifecycle.HiltViewModel
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
        }
    }

    private suspend fun updateCachedUtilities(month: CalendarMonth) {
        val day = month.yearMonth.monthValue
        val year = month.yearMonth.year

        val prevMonth = utilityRepository.getAllUtilitiesByMonth(day - 1, year)
        val currentMonth = utilityRepository.getAllUtilitiesByMonth(day, year)
        val nextMonth = utilityRepository.getAllUtilitiesByMonth(day + 1, year)

        _uiState.update { currentUiState ->
            currentUiState.copy(
                cachedMonthUtilities = listOf(prevMonth, currentMonth, nextMonth).flatten()
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
        viewModelScope.launch {
            val date = uiState.value.selectedDay?.date
            val day = date?.dayOfMonth
            val month = date?.monthValue
            val year = date?.year

            val utilities = if (day == null || month == null || year == null) {
                listOf()
            } else {
                uiState.value.cachedMonthUtilities
                    .filter { utility ->
                        val utilityDate = utility.dueDate
                        (utilityDate.year + 1900) == year && (utilityDate.month + 1) == month && utilityDate.date == day
                    }
            }

            _uiState.update { currentUiState ->
                currentUiState.copy(
                    dayUtilities = utilities
                )
            }
        }
    }
}


data class UtilitiesCalendarState(
    val selectedDay: CalendarDay? = null,
    val dayUtilities: List<Utility> = listOf(),

    val cachedMonthUtilities: List<Utility> = listOf(),
)
