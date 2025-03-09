package com.gvituskins.utilityly.presentation.screens.main.more.more

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
class MoreViewModel @Inject constructor(
    private val dataStore: DataStoreUtil
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        MoreState()
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

    fun changeThemeSection(isOpened: Boolean) {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                themSectionOpened = isOpened
            )
        }
    }

    fun updateTheme(type: ThemeType) {
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

data class MoreState(
    val themSectionOpened: Boolean = false,
    val theme: ThemeType = ThemeType.SYSTEM,
)
