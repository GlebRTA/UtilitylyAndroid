package com.gvituskins.utilityly.presentation.screens.main.categories.manageCategory

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ManageCategoryViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(ManageCategoryState())
    val uiState = _uiState.asStateFlow()


}

data class ManageCategoryState(
    val a: Int = 1
)
