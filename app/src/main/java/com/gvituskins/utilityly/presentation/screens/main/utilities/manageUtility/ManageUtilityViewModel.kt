package com.gvituskins.utilityly.presentation.screens.main.utilities.manageUtility

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ManageUtilityViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(ManageUtilityState())
    val uiState = _uiState.asStateFlow()



}

data class ManageUtilityState(
    val a: Int = 0
)
