package com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun <T> rememberDropDownTextFieldSate(initialValue: T, options: List<T>): DropDownTextFieldState<T> {
    return remember { DropDownTextFieldState(initialValue = initialValue, options = options) }
}

@Stable
class DropDownTextFieldState<T>(
    initialValue: T,
    options: List<T>,
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

    fun updateValue(newValue: T) {
        value = newValue
    }

    fun updateOptions(newOptions: List<T>) {
        options = newOptions
    }
}
