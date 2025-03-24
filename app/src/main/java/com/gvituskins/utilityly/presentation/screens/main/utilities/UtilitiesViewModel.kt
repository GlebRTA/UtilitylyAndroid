package com.gvituskins.utilityly.presentation.screens.main.utilities

import androidx.lifecycle.ViewModel
import com.gvituskins.utilityly.domain.models.utilities.Utility
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UtilitiesViewModel @Inject constructor(
    private val utilityRepository: UtilityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaidUtilitiesState())
    val uiState = _uiState.asStateFlow()

    init {

    }

}

sealed interface PaidUtilityEvent {
    data class ChangePaidStatus(val utilityId: Int) : PaidUtilityEvent
}

data class PaidUtilitiesState(
    val utilities: List<Utility> = listOf(),
)
