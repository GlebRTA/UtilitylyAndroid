package com.gvituskins.utilityly.presentation.screens.main.statistics.statTable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.gvituskins.utilityly.R
import com.gvituskins.utilityly.presentation.components.buttons.UlyFilledTonalButton
import com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField.UlyDropDownTextField
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@Composable
fun StatTableScreen(
    navigateToTable: (Int) -> Unit,
    viewModel: StatTableViewModel = hiltViewModel()
) {
    val yearState = viewModel.yearState

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = stringResource(R.string.select_a_year_table))
            UlyDropDownTextField(
                state = yearState,
                textBuilder = { it?.toString() ?: "" },
                modifier = Modifier.padding(
                    top = UlyTheme.spacing.medium,
                    bottom = UlyTheme.spacing.xxLarge
                )
            )
        }

        UlyFilledTonalButton(
            onClick = { yearState.value?.let { navigateToTable(it) } },
            modifier = Modifier
                .fillMaxWidth()
                .padding(UlyTheme.spacing.mediumSmall),
            enabled = yearState.value != null
        ) {
            Text(text = stringResource(R.string.show_table))
        }
    }
}