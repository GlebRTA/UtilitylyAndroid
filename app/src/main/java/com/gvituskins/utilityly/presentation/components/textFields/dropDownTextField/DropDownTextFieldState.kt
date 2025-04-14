package com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberDropDownTextFieldSate(initialValue: String, options: List<String>): DropDownTextFieldState {
    return remember { DropDownTextFieldState(initialValue = initialValue, options = options) }
}

@Stable
class DropDownTextFieldState(
    initialValue: String,
    options: List<String>
) {

    var value by mutableStateOf(initialValue)
        private set

    var isExpanded by mutableStateOf(false)
        private set

    var options by mutableStateOf(options)
        private set

    fun updateExpand(expanded: Boolean) {
        isExpanded = expanded
    }

    fun updateValue(newValue: String) {
        value = newValue
    }

    fun updateOptions(newOptions: List<String>) {
        options = newOptions
    }
}
