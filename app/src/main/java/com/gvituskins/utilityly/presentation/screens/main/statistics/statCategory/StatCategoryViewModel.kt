package com.gvituskins.utilityly.presentation.screens.main.statistics.statCategory

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.domain.repositories.CategoryRepository
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField.DropDownTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class StatCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val utilityRepository: UtilityRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatCategoryState())
    val uiState = _uiState.asStateFlow()

    fun updateChartInfo() {
        viewModelScope.launch {
            val categories = categoryRepository.getAllCategories().first()
            val rows = mutableListOf<YearUtilitiesAmount>()
            categories.forEach { category ->
                val yearUtilitiesAmount = utilityRepository
                    .getAllUtilitiesByYear(year = uiState.value.yearState.value)
                    .filter { it.category.id == category.id }
                    .sumOf { it.amount }

                rows.add(
                    YearUtilitiesAmount(
                        color = Color(
                            red = Random.nextInt(256),
                            green = Random.nextInt(256),
                            blue = Random.nextInt(256)
                        ),
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
    val yearState: DropDownTextFieldState<Int> = DropDownTextFieldState(initialValue = 2025, options = listOf(2024, 2025)),

    val tableRows: List<YearUtilitiesAmount> = listOf()
)

@Immutable
data class YearUtilitiesAmount(
    val color: Color,
    val name: String,
    val percent: Double,
    val amount: Double
)
