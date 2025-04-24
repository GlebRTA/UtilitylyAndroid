package com.gvituskins.utilityly.presentation.screens.main.utilities.grid

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.domain.models.enums.PaidStatus
import com.gvituskins.utilityly.domain.models.utilities.Utility
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class UtilitiesGridViewModel @Inject constructor(
    private val utilityRepository: UtilityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UtilitiesGridState())
    val uiState = _uiState.asStateFlow()

    init {
        updateMonth()
    }

    fun nextMonth() {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                currentMont = currentUiState.currentMont.nextMonth
            )
        }
        updateMonth()
    }

    fun previousMonth() {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                currentMont = currentUiState.currentMont.previousMonth
            )
        }
        updateMonth()
    }

    private fun updateMonth() {
        viewModelScope.launch {
            val newUtils = utilityRepository.getAllUtilitiesInMonth(
                month = uiState.value.currentMont.monthValue,
                year = uiState.value.currentMont.year,
            )
            _uiState.update { currentUiState ->
                currentUiState.copy(
                    utilities = newUtils
                )
            }
        }
    }
}

@Immutable
data class UtilitiesGridState(
    val utilities: List<Utility> = emptyList(),

    val currentMont: YearMonth = YearMonth.now()
) {
    val total: Double
        get() = utilities.map { it.amount }.sum()

    val unpaid: Double
        get() = utilities
            .filter { it.paidStatus == PaidStatus.UNPAID }
            .map { it.amount }.sum()

    val paid: Double
        get() = utilities
            .filter { it.paidStatus == PaidStatus.PAID }
            .map { it.amount }.sum()
}
