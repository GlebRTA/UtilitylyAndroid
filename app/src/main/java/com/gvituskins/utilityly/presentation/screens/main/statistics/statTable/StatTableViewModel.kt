package com.gvituskins.utilityly.presentation.screens.main.statistics.statTable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField.DropDownTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StatTableViewModel @Inject constructor(
    utilityRepository: UtilityRepository
) : ViewModel() {

    val yearState = DropDownTextFieldState<Int?>(initialValue = null, options = listOf())

    init {
        utilityRepository.getAllUtilities()
            .onEach {
                val years = utilityRepository.getAllAvailableYears()
                yearState.updateOptions(years)
                if (yearState.value == null) {
                    yearState.updateValue(years.getOrNull(0))
                }
            }
            .launchIn(viewModelScope)
    }
}
