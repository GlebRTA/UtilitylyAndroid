package com.gvituskins.utilityhelper.presentation.screens.main.unpaidUtilitiesFeature.detailsUnpaidUtilities

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.gvituskins.utilityhelper.domain.models.utilities.Utility
import com.gvituskins.utilityhelper.domain.repositories.UtilityRepository
import com.gvituskins.utilityhelper.presentation.screens.main.unpaidUtilitiesFeature.UnpaidNavGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsUnpaidViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val utilityRepository: UtilityRepository
) : ViewModel() {
    private val arguments = savedStateHandle.toRoute<UnpaidNavGraph.DetailsUnpaid>()

    private val _uiState = MutableStateFlow(DetailsUnpaidState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    utility = utilityRepository.getUtilityById(arguments.utilityId)
                )
            }
        }
    }

    fun delete() {
        uiState.value.utility?.id?.let { utilityId ->
            viewModelScope.launch {
                utilityRepository.deleteUtility(utilityId)
            }
        }
    }
}

data class DetailsUnpaidState(
    val utility: Utility? = null
)
