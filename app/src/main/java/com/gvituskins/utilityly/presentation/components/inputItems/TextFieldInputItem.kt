package com.gvituskins.utilityly.presentation.components.inputItems

import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gvituskins.utilityly.presentation.components.textFields.UlyOutlinedTextFiled

@Composable
fun TextFieldInputItem(
    title: String?,
    textFiledState: TextFieldState,
    isError: Boolean = false,
    errorText: String? = null,
    placeholderText: String? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.Default,
    modifier: Modifier = Modifier
) {
    TextInputItem(
        title = title,
        modifier = modifier
    ) {
        UlyOutlinedTextFiled(
            state = textFiledState,
            placeholder = {
                placeholderText?.let {
                    Text(text = it)
                }
            },
            lineLimits = lineLimits,
            isError = isError,
            supportingText = {
                if (isError && errorText != null) {
                    Text(text = errorText)
                }
            }
        )
    }
}
