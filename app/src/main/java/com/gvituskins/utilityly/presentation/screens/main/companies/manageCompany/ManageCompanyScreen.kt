package com.gvituskins.utilityly.presentation.screens.main.companies.manageCompany

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.R
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.containers.ManageContainer
import com.gvituskins.utilityly.presentation.components.inputItems.TextFieldInputItem
import com.gvituskins.utilityly.presentation.core.utils.collectAsOneTimeEvent
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@Composable
fun ManageCompanyScreen(
    navigateBack: () -> Unit,
    viewModel: ManageCompanyViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.label.collectAsOneTimeEvent { event ->
        when (event) {
            ManageCompanyOneTime.NavigateBack -> navigateBack()
        }
    }

    ManageContainer(
        navigateBack = navigateBack,
        titleText = stringResource(if (uiState.isAddMode) R.string.title_add_company else R.string.title_edit_company),
        buttonText = stringResource(if (uiState.isAddMode) R.string.add else R.string.edit),
        onButtonClick = { viewModel.manageCategory() },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(UlyTheme.spacing.mediumLarge)
        ) {
            TextFieldInputItem(
                title = stringResource(R.string.name),
                textFiledState = uiState.name,
                placeholderText = stringResource(R.string.company_name),
                isError = uiState.nameIsError,
                errorText = "Company name should not be empty"
            )

            TextFieldInputItem(
                title = stringResource(R.string.address),
                textFiledState = uiState.address,
                placeholderText = "Company Address",
            )

            TextFieldInputItem(
                title = "Phone",
                textFiledState = uiState.phone,
                placeholderText = stringResource(R.string.company_phone),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                )
            )

            TextFieldInputItem(
                title = "WEB Page",
                textFiledState = uiState.webPage,
                placeholderText = stringResource(R.string.company_web_page),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri
                )
            )

            TextFieldInputItem(
                title = "e-mail",
                textFiledState = uiState.email,
                placeholderText = stringResource(R.string.company_e_mail),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )
        }

        VerticalSpacer(ButtonDefaults.MinHeight * 2)
    }
}
