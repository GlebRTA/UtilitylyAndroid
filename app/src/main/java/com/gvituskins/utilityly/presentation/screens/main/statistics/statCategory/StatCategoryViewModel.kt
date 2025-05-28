package com.gvituskins.utilityly.presentation.screens.main.statistics.statCategory

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.data.preferences.DataStoreUtil
import com.gvituskins.utilityly.domain.repositories.CategoryRepository
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField.DropDownTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatCategoryViewModel @Inject constructor(
    preferences: DataStoreUtil,
    private val categoryRepository: CategoryRepository,
    private val utilityRepository: UtilityRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatCategoryState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            utilityRepository.getAllUtilities()
                .onEach { updateStatistics() }
                .launchIn(viewModelScope)
        }

        preferences.locationId()
            .onEach {
                _uiState.update { StatCategoryState() }
                updateStatistics()
            }
            .launchIn(viewModelScope)
    }

    private suspend fun updateStatistics() {
        val years = utilityRepository.getAllAvailableYears()
        uiState.value.yearState.updateOptions(years)
        if (uiState.value.yearState.value == null || years.isEmpty()) {
            uiState.value.yearState.updateValue(years.getOrNull(0))
        }
        updateChartInfo()
    }

    fun updateChartInfo() {
        val currentYear = uiState.value.yearState.value
        if (currentYear == null) {
            _uiState.update { currentUiState ->
                currentUiState.copy(tableRows = listOf())
            }
            return
        }

        viewModelScope.launch {
            val categories = categoryRepository.getAllCategories().first()
            val rows = mutableListOf<YearUtilitiesAmount>()
            categories.forEach { category ->
                val yearUtilitiesAmount = utilityRepository
                    .getAllUtilitiesByYear(year = currentYear)
                    .filter { it.category.id == category.id }
                    .sumOf { it.amount }

                rows.add(
                    YearUtilitiesAmount(
                        color = category.color,
                        name = category.name,
                        percent = 0.0,
                        amount = yearUtilitiesAmount
                    )
                )
            }

            val total = rows.sumOf { it.amount }
            val newRows = rows.map { row ->
                row.copy(
                    percent = row.amount * 100.0 / total
                )
            }

            _uiState.update { currentUiState ->
                currentUiState.copy(
                    tableRows = newRows
                )
            }
        }
    }
}

@Immutable
data class StatCategoryState(
    val yearState: DropDownTextFieldState<Int?> = DropDownTextFieldState(
        initialValue = null,
        options = listOf<Int?>()
    ),

    val tableRows: List<YearUtilitiesAmount> = listOf()
)

@Immutable
data class YearUtilitiesAmount(
    val color: Color,
    val name: String,
    val percent: Double,
    val amount: Double
)
