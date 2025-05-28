package com.gvituskins.utilityly.presentation.screens.main.statistics.statYears

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.data.preferences.DataStoreUtil
import com.gvituskins.utilityly.domain.models.categories.Category
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
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class StatYearsViewModel @Inject constructor(
    categoryRepository: CategoryRepository,
    preferences: DataStoreUtil,
    private val utilityRepository: UtilityRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatYearsState())
    val uiState = _uiState.asStateFlow()

    private var allAvailableYears: List<Int> = listOf()

    init {
        categoryRepository.getAllCategories()
            .onEach { categories -> initStatistics(categories) }
            .launchIn(viewModelScope)

        preferences.locationId()
            .onEach { initStatistics(categoryRepository.getAllCategories().first()) }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            utilityRepository.getAllUtilities()
                .onEach {
                    val newAllAvailableYears = utilityRepository.getAllAvailableYears()
                    if (allAvailableYears == newAllAvailableYears) {
                        updateChartWithSavedYears()
                    } else {
                        initStatistics(categoryRepository.getAllCategories().first())
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private suspend fun initStatistics(categories: List<Category>, restoreState: Boolean = true) {
        if (restoreState) {
            _uiState.update { StatYearsState() }
        }

        allAvailableYears = utilityRepository.getAllAvailableYears()

        uiState.value.categoryState.updateOptions(categories)
        if (uiState.value.categoryState.value == null || categories.isEmpty()) {
            uiState.value.categoryState.updateValue(categories.getOrNull(0))
        }
        _uiState.update { currentUiState ->
            currentUiState.copy(
                availableYears = allAvailableYears
            )
        }
        updateChartWithSavedYears()
    }

    fun updateLine(
        id: String,
        newYear: Int
    ) {
        viewModelScope.launch {
            val category = uiState.value.categoryState.value ?: return@launch
            val line = buildLine(
                category = category,
                year = newYear
            )

            val newList = uiState.value.yearWithLine.toMutableList().apply {
                val oldYearWithLine = find { it.id == id } ?: return@launch
                val newYearWithLine = oldYearWithLine.copy(
                    year = newYear,
                    line = line
                )
                val index = indexOf(oldYearWithLine)
                removeAt(index)
                add(index, newYearWithLine)
            }

            val usedYears = newList.map { it.year }
            val newAvailableYears = allAvailableYears.subtract(usedYears.toSet()).toList()

            _uiState.update { currentUiState ->
                currentUiState.copy(
                    yearWithLine = newList,
                    availableYears = newAvailableYears
                )
            }
        }
    }

    fun addLine(year: Int) {
        viewModelScope.launch {
            val category = uiState.value.categoryState.value ?: return@launch

            val line = buildLine(
                category = category,
                year = year
            )

            val newList = uiState.value.yearWithLine.toMutableList().apply {
                add(
                    YearWithLine(
                        year = year,
                        line = line
                    )
                )
            }

            val years = uiState.value.availableYears.toMutableList().apply {
                remove(element = year)
            }

            _uiState.update { currentUiState ->
                currentUiState.copy(
                    yearWithLine = newList,
                    availableYears = years
                )
            }
        }
    }

    fun removeLine(year: Int) {
        val yearWithLine = uiState.value.yearWithLine.find { it.year == year } ?: return

        val newList = uiState.value.yearWithLine.toMutableList().apply {
            remove(yearWithLine)
        }

        val usedYears = newList.map { it.year }
        val newAvailableYears = allAvailableYears.subtract(usedYears.toSet()).toList()

        _uiState.update { currentUiState ->
            currentUiState.copy(
                yearWithLine = newList,
                availableYears = newAvailableYears
            )
        }
    }

    private suspend fun buildLine(
        category: Category,
        year: Int
    ): UlyChartLine {
        val categoryMonthsAmounts = mutableListOf<Double>()
        (1..12).forEach { month ->
            val categoryUtilitiesPerMonthAmount = utilityRepository.getAllUtilitiesByMonth(month, year)
                .filter { monthUtility ->
                    monthUtility.category.id == category.id && monthUtility.dueDate.monthValue == month
                }
                .sumOf { it.amount }

            categoryMonthsAmounts.add(categoryUtilitiesPerMonthAmount)
        }

        return UlyChartLine(
            label = category.name,
            values = categoryMonthsAmounts,
        )
    }

    fun updateChartWithSavedYears() {
        viewModelScope.launch {
            _uiState.update { currentUiState ->
                currentUiState.copy(
                    yearWithLine = uiState.value.yearWithLine.map { yearWithLine ->
                        yearWithLine.copy(
                            line = buildLine(
                                category = uiState.value.categoryState.value ?: return@launch,
                                year = yearWithLine.year
                            )
                        )
                    },
                )
            }
        }
    }
}

@Immutable
data class StatYearsState(
    val categoryState: DropDownTextFieldState<Category?> = DropDownTextFieldState(initialValue = null, listOf()),

    val yearWithLine: List<YearWithLine> = listOf(),
    val availableYears: List<Int> = listOf()
)

@Immutable
data class YearWithLine(
    val id: String = UUID.randomUUID().toString(),
    val year: Int,
    val line: UlyChartLine
)

@Immutable
data class UlyChartLine(
    val label: String,
    val values: List<Double>
)
