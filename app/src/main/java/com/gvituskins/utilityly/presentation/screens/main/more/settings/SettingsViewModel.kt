package com.gvituskins.utilityly.presentation.screens.main.more.settings

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStoreUtil
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        SettingsState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        dataStore
            .currentTheme()
            .onEach { newTheme ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(theme = newTheme)
                }
            }
            .launchIn(viewModelScope)
    }

    fun update(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.UpdateThemeType -> updateTheme(event.type)
        }
    }

    private fun updateTheme(type: ThemeType) {
        viewModelScope.launch {
            dataStore.changeTheme(type)
            _uiState.update { currentUiState ->
                currentUiState.copy(
                    theme = type
                )
            }
        }
    }
}

sealed interface SettingsEvent {
    data class UpdateThemeType(val type: ThemeType) : SettingsEvent
}

data class SettingsState(
    val theme: ThemeType = ThemeType.SYSTEM
)
