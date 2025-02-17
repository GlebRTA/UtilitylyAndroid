package com.gvituskins.utilityly.presentation.screens.main.more.settings

import androidx.lifecycle.ViewModel
import com.gvituskins.utilityly.data.preferences.UlySharedPreferences
import com.gvituskins.utilityly.domain.models.enums.ThemeType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: UlySharedPreferences
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        SettingsState(theme = preferences.themeType)
    )
    val uiState = _uiState.asStateFlow()

    fun update(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.UpdateThemeType -> updateTheme(event.type)
        }
    }

    private fun updateTheme(type: ThemeType) {
        preferences.themeType = type
        _uiState.update { currentUiState ->
            currentUiState.copy(
                theme = type
            )
        }
    }

}

sealed interface SettingsEvent {
    data class UpdateThemeType(val type: ThemeType) : SettingsEvent
}

data class SettingsState(
    val theme: ThemeType
)
