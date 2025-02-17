package com.gvituskins.utilityly.presentation.screens.main.fact.homeFact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.domain.models.Fact
import com.gvituskins.utilityly.domain.models.common.NetworkResult
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val utilityRepository: UtilityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FactState())
    val uiState = _uiState.asStateFlow()

    init {
        getRandomFact()
    }

    fun getRandomFact() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, error = null)
            }
            when (val result = utilityRepository.getRandomFact()) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            facts = result.data,
                            isLoading = false
                        )
                    }
                }
                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Error: ${result.message}"
                        )
                    }
                }
            }
        }
    }
}

data class FactState(
    val facts: Fact? = null,

    val isLoading: Boolean = false,
    val error: String? = null,
)
