package com.gvituskins.utilityly.presentation.screens.main.utilities.utilityDetails

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.gvituskins.utilityly.domain.models.utilities.Utility
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import com.gvituskins.utilityly.presentation.navigation.graphs.UtilitiesNavGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UtilityDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val utilityRepository: UtilityRepository,
) : ViewModel() {

    private val initUtility = savedStateHandle.toRoute<UtilitiesNavGraph.UtilityDetails>()

    private val _uiState = MutableStateFlow(UtilityDetailsState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val utility = utilityRepository.getUtilityById(initUtility.utilityId)
            val prevUtility = utilityRepository.getPreviousUtility(
                utilityId = utility.id,
                categoryId = utility.category.id,
                date = utility.dueDate
            )

            val difference = prevUtility?.amount?.let { oldAmount -> utility.amount - oldAmount }
            val differenceInPercent = difference?.let { dif -> dif / prevUtility.amount }

            val detailsCompare = if (difference != null && differenceInPercent != null) {
                CompareAmount(
                    amountDif = difference.toFloat(),
                    percentDif = differenceInPercent.toFloat()
                )
            } else null

            _uiState.update { currentUiState ->
                currentUiState.copy(
                    utility = utility,
                    prevUtility = prevUtility,
                    detailsCoast = getDetailsCoast(utility),
                    detailsCompare = detailsCompare
                )
            }
        }
    }

    private suspend fun getDetailsCoast(utility: Utility): Float {
        val totalSum = utilityRepository.getAllUtilitiesByMonth(
            month = utility.dueDate.monthValue,
            year = utility.dueDate.year,
        ).sumOf { it.amount }
        return ((utility.amount * 100 / totalSum) / 100f).toFloat()
    }

    fun changePaidStatus() {
        viewModelScope.launch {
            uiState.value.utility?.id?.let { utilityId ->
                utilityRepository.changePaidStatus(utilityId)
                val newUtility = utilityRepository.getUtilityById(utilityId)

                _uiState.update { currentUiState ->
                    currentUiState.copy(utility = newUtility)
                }
            }
        }
    }

}

@Immutable
data class UtilityDetailsState(
    val utility: Utility? = null,
    val prevUtility: Utility? = null,

    val detailsCoast: Float = 0f,
    val detailsCompare: CompareAmount? = CompareAmount(0f, 0f),
)

@Immutable
data class CompareAmount(
    val amountDif: Float,
    val percentDif: Float
)