package com.gvituskins.utilityly.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvituskins.utilityly.data.preferences.DataStoreUtil
import com.gvituskins.utilityly.domain.models.enums.ThemeType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStore: DataStoreUtil
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val uiState = _uiState.asStateFlow()

    init {
        consumeThemeType()
    }

    private fun consumeThemeType() {
        dataStore
            .currentTheme()
            .onEach { type ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(themeType = type)
                }
            }
            .launchIn(viewModelScope)
    }
}

data class MainState(
    val themeType: ThemeType = ThemeType.SYSTEM
)
