package com.gvituskins.vituskinsandroid.presentation.main.unpaidUtilitiesFeature.homeUnpaidUtilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.vituskinsandroid.data.repositories.UtilityRepository
import com.gvituskins.vituskinsandroid.domain.models.Utility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        getAllUnpaid()
    }

    fun update(event: HomeUnpaidEvent) {
        when (event) {
            is HomeUnpaidEvent.CreateNewUtility -> createNewUtility(event.name, event.description)
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

    private fun getAllUnpaid() {
        viewModelScope.launch {
            _uiState.update { currentUiState ->
                currentUiState.copy(
                    utilities = repository.getAllUnpaidUtilities()
                )
            }
        }
    }

}

sealed interface HomeUnpaidEvent {
    data class CreateNewUtility(
        val name: String,
        val description: String
    ): HomeUnpaidEvent
}

data class HomeUnpaidState(
    val utilities: List<Utility> = listOf()
)
