package com.gvituskins.utilityly.presentation.screens.main.utilities.utilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.domain.models.utilities.Utility
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UtilitiesViewModel @Inject constructor(
    private val utilityRepository: UtilityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaidUtilitiesState())
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

    private fun changeUtilityPaidStatus(id: Int) {
        viewModelScope.launch {
            utilityRepository.changePaidStatus(id)
        }
    }
}

sealed interface PaidUtilityEvent {
    data class ChangePaidStatus(val utilityId: Int) : PaidUtilityEvent
}

data class PaidUtilitiesState(
    val utilities: List<Utility> = listOf()
)
