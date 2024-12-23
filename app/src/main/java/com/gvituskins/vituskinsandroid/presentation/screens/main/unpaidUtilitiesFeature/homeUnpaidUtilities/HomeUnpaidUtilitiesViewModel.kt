package com.gvituskins.vituskinsandroid.presentation.screens.main.unpaidUtilitiesFeature.homeUnpaidUtilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.vituskinsandroid.domain.models.Utility
import com.gvituskins.vituskinsandroid.domain.repositories.UtilityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
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
            is HomeUnpaidEvent.CreateNewUtility -> createNewUtility(event.name, event.description)
            is HomeUnpaidEvent.ChangePaidStatus -> changePaidStatus(event.utilityId)
            is HomeUnpaidEvent.ChangeAddNewBS -> changeAddNewBsState(event.toVisibility)
        }
    }

    private fun createNewUtility(name: String, description: String) {
        viewModelScope.launch {
            val newUtility = Utility(
                name = name,
                description = description,
                date = Date().toLocaleString(),
                isPaid = false
            )

            repository.addNewUtility(newUtility)
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
        val description: String
    ): HomeUnpaidEvent

    data class ChangePaidStatus(val utilityId: Int) : HomeUnpaidEvent

    data class ChangeAddNewBS(val toVisibility: Boolean) : HomeUnpaidEvent
}

data class HomeUnpaidState(
    val utilities: List<Utility> = listOf(),
    val showAddNewBottomSheet: Boolean = false,
)
