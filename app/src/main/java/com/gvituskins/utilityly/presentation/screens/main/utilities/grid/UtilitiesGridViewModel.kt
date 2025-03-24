package com.gvituskins.utilityly.presentation.screens.main.utilities.grid

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UtilitiesGridViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(UtilitiesGridState())
    val uiState = _uiState.asStateFlow()

}

@Immutable
data class UtilitiesGridState(
    val paramOne: String = "",
)
