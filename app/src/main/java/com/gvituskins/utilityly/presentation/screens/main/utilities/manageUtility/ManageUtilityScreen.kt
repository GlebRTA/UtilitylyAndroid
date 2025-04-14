package com.gvituskins.utilityly.presentation.screens.main.utilities.manageUtility

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.buttons.UlyOutlinedButton
import com.gvituskins.utilityly.presentation.components.containers.ManageContainer
import com.gvituskins.utilityly.presentation.components.inputItems.TextInputItem
import com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField.UlyDropDownTextField
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@Composable
fun ManageUtilityScreen(
    navigateBack: () -> Unit,
    navigateToAddCategory: () -> Unit,
    navigateToAddCompany: () -> Unit,
    viewModel: ManageUtilityViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ManageContainer(
        navigateBack = navigateBack,
        titleText = "Edit Utility",
        buttonText = "Edit",
        onButtonClick = {}
    ) {
        TextInputItem(title = "Category") {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                UlyDropDownTextField(
                    state = uiState.categoryDropState,
                    modifier = Modifier.weight(1.8f)
                )

                UlyOutlinedButton(
                    onClick = { navigateToAddCategory() },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(horizontal = UlyTheme.spacing.mediumSmall)
                ) {
                    Text(text = "Add new category")
                }
            }
        }

        VerticalSpacer(UlyTheme.spacing.large)

        TextInputItem(title = "Company Name") {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                UlyDropDownTextField(
                    state = uiState.companyDropState,
                    modifier = Modifier.weight(1.8f)
                )

                UlyOutlinedButton(
                    onClick = { navigateToAddCompany() },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(horizontal = UlyTheme.spacing.mediumSmall)
                ) {
                    Text(text = "Add new company")
                }
            }
        }
    }
}
