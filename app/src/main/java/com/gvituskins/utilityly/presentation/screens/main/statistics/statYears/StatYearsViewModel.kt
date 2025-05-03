package com.gvituskins.utilityly.presentation.screens.main.statistics.statYears

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.domain.models.categories.Category
import com.gvituskins.utilityly.domain.repositories.CategoryRepository
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField.DropDownTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.Line
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class StatYearsViewModel @Inject constructor(
    categoryRepository: CategoryRepository,
    private val utilityRepository: UtilityRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatYearsState())
    val uiState = _uiState.asStateFlow()

    private lateinit var allAvailableYears: List<Int>

    init {
        categoryRepository.getAllCategories()
            .onEach { categories ->
                allAvailableYears = utilityRepository.getAllAvailableYears()

                uiState.value.categoryState.updateOptions(categories)
                if (uiState.value.categoryState.value == null) {
                    uiState.value.categoryState.updateValue(categories.getOrNull(0))
                }
                initChart()
            }
            .launchIn(viewModelScope)
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

            val newList = uiState.value.yearWIthLine.toMutableList().apply {
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
            val newAvailableYears = allAvailableYears.subtract(usedYears).toList()

            _uiState.update { currentUiState ->
                currentUiState.copy(
                    yearWIthLine = newList,
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

            val newList = uiState.value.yearWIthLine.toMutableList().apply {
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
                    yearWIthLine = newList,
                    availableYears = years
                )
            }
        }
    }

    fun removeLine(year: Int) {
        val year = uiState.value.yearWIthLine.find { it.year == year } ?: return

        val newList = uiState.value.yearWIthLine.toMutableList().apply {
            remove(year)
        }

        val usedYears = newList.map { it.year }
        val newAvailableYears = allAvailableYears.subtract(usedYears).toList()

        _uiState.update { currentUiState ->
            currentUiState.copy(
                yearWIthLine = newList,
                availableYears = newAvailableYears
            )
        }
    }

    private suspend fun buildLine(
        category: Category,
        year: Int
    ): Line {
        val categoryMonthsAmounts = mutableListOf<Double>()
        (1..12).forEach { month ->
            val categoryUtilitiesPerMonthAmount = utilityRepository.getAllUtilitiesByMonth(month, year)
                .filter { monthUtility ->
                    monthUtility.category.id == category.id && (monthUtility.dueDate.month + 1) == month
                }
                .sumOf { it.amount }

            categoryMonthsAmounts.add(categoryUtilitiesPerMonthAmount)
        }

        val color = Color.Yellow
        return Line(
            label = category.name,
            values = categoryMonthsAmounts,
            color = SolidColor(color),
            firstGradientFillColor = color.copy(alpha = .5f),
            secondGradientFillColor = Color.Transparent,
            strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
            gradientAnimationDelay = 1000,
            dotProperties = DotProperties(enabled = true, color = SolidColor(color))
        )
    }

    fun initChart() {
        viewModelScope.launch {
            _uiState.update { currentUiState ->
                currentUiState.copy(
                    yearWIthLine = uiState.value.yearWIthLine.map { yearWithLine ->
                        yearWithLine.copy(
                            line = buildLine(
                                category = uiState.value.categoryState.value ?: return@launch,
                                year = yearWithLine.year
                            )
                        )
                    },
                    availableYears = allAvailableYears
                )
            }
        }
    }
}

@Immutable
data class StatYearsState(
    val categoryState: DropDownTextFieldState<Category?> = DropDownTextFieldState(initialValue = null, listOf()),

    val yearWIthLine: List<YearWithLine> = listOf(),
    val availableYears: List<Int> = listOf()
)

@Immutable
data class YearWithLine(
    val id: String = UUID.randomUUID().toString(),
    val year: Int,
    val line: Line
)
