package com.gvituskins.utilityly.presentation.screens.main.utilities.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.domain.models.utilities.Utility
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import com.gvituskins.utilityly.presentation.screens.main.utilities.PaidUtilityEvent
import com.kizitonwose.calendar.core.CalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UtilitiesCalendarViewModel @Inject constructor(
    private val utilityRepository: UtilityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UtilitiesCalendarState())
    val uiState = _uiState.asStateFlow()

    init {
        utilityRepository.getAllPaidUtilities()
            .onEach { updatedList ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        utilities = updatedList
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun update(event: PaidUtilityEvent) {
        when (event) {
            is PaidUtilityEvent.ChangePaidStatus -> changeUtilityPaidStatus(event.utilityId)
        }
    }

    fun updateSelectedDay(newDay: CalendarDay) {
        _uiState.update { currentUiState ->
            currentUiState.copy(selectedDay = newDay)
        }
    }

    private fun changeUtilityPaidStatus(id: Int) {
        viewModelScope.launch {
            utilityRepository.changePaidStatus(id)
        }
    }
}


data class UtilitiesCalendarState(
    val selectedDay: CalendarDay? = null,
    val selectedMonth: CalendarDay? = null,

    val utilities: List<Utility> = listOf(),
)
