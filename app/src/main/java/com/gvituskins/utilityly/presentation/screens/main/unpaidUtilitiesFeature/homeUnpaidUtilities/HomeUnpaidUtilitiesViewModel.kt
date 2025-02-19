package com.gvituskins.utilityly.presentation.screens.main.unpaidUtilitiesFeature.homeUnpaidUtilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.domain.models.utilities.CreateUtility
import com.gvituskins.utilityly.domain.models.utilities.Utility
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import com.gvituskins.utilityly.presentation.core.utils.debugLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeUnpaidUtilitiesViewModel @Inject constructor(
    private val repository: UtilityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUnpaidState())
    val uiState = _uiState.asStateFlow()

    init {
        repository.getAllUnpaidUtilities()
            .onEach { newList ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        utilities = newList
                    )
                }
            }
            .launchIn(viewModelScope)

    }

    fun update(event: HomeUnpaidEvent) {
        when (event) {
            is HomeUnpaidEvent.CreateNewUtility -> createNewUtility(event.name, event.description, event.amount)
            is HomeUnpaidEvent.ChangePaidStatus -> changePaidStatus(event.utilityId)
            is HomeUnpaidEvent.ChangeAddNewBS -> changeAddNewBsState(event.toVisibility)
        }
    }

    private fun createNewUtility(name: String, description: String, amount: String) {
        debugLog("Amount = $amount")
        amount.toDoubleOrNull()?.let { validAmount ->
            viewModelScope.launch {
                repository.addNewUtility(
                    CreateUtility(
                        name = name,
                        description = description,
                        amount = validAmount
                    )
                )
            }
        }
    }

    private fun changePaidStatus(utilityId: Int) {
        viewModelScope.launch {
            repository.changePaidStatus(utilityId)
        }
    }

    private fun changeAddNewBsState(toVisibility: Boolean) {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                showAddNewBottomSheet = toVisibility
            )
        }
    }
}

sealed interface HomeUnpaidEvent {
    data class CreateNewUtility(
        val name: String,
        val description: String,
        val amount: String,
    ): HomeUnpaidEvent

    data class ChangePaidStatus(val utilityId: Int) : HomeUnpaidEvent

    data class ChangeAddNewBS(val toVisibility: Boolean) : HomeUnpaidEvent
}

data class HomeUnpaidState(
    val utilities: List<Utility> = listOf(),
    val showAddNewBottomSheet: Boolean = false,
)
