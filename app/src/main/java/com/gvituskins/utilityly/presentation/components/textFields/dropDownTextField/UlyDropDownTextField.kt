package com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> UlyDropDownTextField(
    state: DropDownTextFieldState<T>,
    textBuilder: (T) -> String,
    modifier: Modifier = Modifier
) {
    ExposedDropdownMenuBox(
        expanded = state.isExpanded,
        onExpandedChange = { state.updateExpand(it) },
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor(
                type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
            ),
            value = textBuilder(state.value),
            onValueChange = {  },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.isExpanded)
            },
        )

        ExposedDropdownMenu(
            expanded = state.isExpanded,
            onDismissRequest = { state.updateExpand(false) },
        ) {
            state.options.forEach { textValue ->
                DropdownMenuItem(
                    text = { Text(text = textBuilder(textValue)) },
                    onClick = {
                        state.updateValue(textValue)
                        state.updateExpand(false)
                    },
                )
            }
        }
    }
}
