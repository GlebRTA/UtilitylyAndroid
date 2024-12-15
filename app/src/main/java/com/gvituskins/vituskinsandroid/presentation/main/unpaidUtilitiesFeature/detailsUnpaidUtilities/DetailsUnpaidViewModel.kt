package com.gvituskins.vituskinsandroid.presentation.main.unpaidUtilitiesFeature.detailsUnpaidUtilities

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.gvituskins.vituskinsandroid.data.repositories.UtilityRepository
import com.gvituskins.vituskinsandroid.domain.models.Utility
import com.gvituskins.vituskinsandroid.presentation.main.unpaidUtilitiesFeature.UnpaidNavGraph
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

    fun update(event: DetailsUnpaidEvent) {
        when (event) {
            else -> {}
        }
    }
}

sealed interface DetailsUnpaidEvent {

}

data class DetailsUnpaidState(
    val utility: Utility? = null
)
