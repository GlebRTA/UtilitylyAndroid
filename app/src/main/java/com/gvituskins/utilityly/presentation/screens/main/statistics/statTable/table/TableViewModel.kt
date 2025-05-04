package com.gvituskins.utilityly.presentation.screens.main.statistics.statTable.table

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.gvituskins.utilityly.domain.repositories.CategoryRepository
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import com.gvituskins.utilityly.presentation.core.utils.roundToStr
import com.gvituskins.utilityly.presentation.navigation.graphs.StatisticsNavGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TableViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    categoryRepository: CategoryRepository,
    utilityRepository: UtilityRepository,
) : ViewModel() {
    private val year = savedStateHandle.toRoute<StatisticsNavGraph.Table>().year

    private val _uiState = MutableStateFlow(TableState(year = year))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val categories = categoryRepository.getAllCategories().first()
            val tableRows = mutableListOf<TableRowModel>()
            categories.forEach { category ->
                val categoryMonthsAmounts = mutableListOf<String>()
                (1..12).forEach { month ->
                    val categoryUtilitiesPerMonthAmount = utilityRepository.getAllUtilitiesByMonth(month, year)
                        .filter { monthUtility ->
                            monthUtility.category.id == category.id && monthUtility.dueDate.monthValue == month
                        }
                        .sumOf { it.amount }
                        .roundToStr()
                    categoryMonthsAmounts.add("$$categoryUtilitiesPerMonthAmount")
                }
                tableRows.add(
                    TableRowModel(
                        name = category.name,
                        months = categoryMonthsAmounts
                    )
                )
            }

            _uiState.update { currentUiState ->
                currentUiState.copy(
                    tableRows = tableRows
                )
            }
        }
    }
}

@Immutable
data class TableState(
    val year: Int,
    val tableRows: List<TableRowModel> = listOf()
)

@Immutable
data class TableRowModel(
    val name: String,
    val months: List<String>
)