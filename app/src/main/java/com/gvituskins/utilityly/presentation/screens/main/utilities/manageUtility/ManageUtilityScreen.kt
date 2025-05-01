package com.gvituskins.utilityly.presentation.screens.main.utilities.manageUtility

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.R
import com.gvituskins.utilityly.domain.models.enums.UtilityRepeat
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.buttons.UlyOutlinedButton
import com.gvituskins.utilityly.presentation.components.buttons.segmented.CenterSegmentedButton
import com.gvituskins.utilityly.presentation.components.buttons.segmented.EndSegmentedButton
import com.gvituskins.utilityly.presentation.components.buttons.segmented.StartSegmentedButton
import com.gvituskins.utilityly.presentation.components.containers.ManageContainer
import com.gvituskins.utilityly.presentation.components.inputItems.TextInputItem
import com.gvituskins.utilityly.presentation.components.pickers.DatePickerModal
import com.gvituskins.utilityly.presentation.components.textFields.UlyOutlinedTextFiled
import com.gvituskins.utilityly.presentation.components.textFields.dropDownTextField.UlyDropDownTextField
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import java.text.SimpleDateFormat

@Composable
fun ManageUtilityScreen(
    navigateBack: () -> Unit,
    navigateToAddCategory: () -> Unit,
    navigateToAddCompany: () -> Unit,
    viewModel: ManageUtilityViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.categoryDropState.value) {
        viewModel.updateCategoryParameters(uiState.categoryDropState.value)
    }

    ManageContainer(
        navigateBack = navigateBack,
        titleText = stringResource(if (uiState.isAddMode) R.string.add_utility else R.string.edit_utility),
        buttonText = stringResource(if (uiState.isAddMode) R.string.add else R.string.edit),
        onButtonClick = { viewModel.addUtility() }
    ) {
        TextInputItem(title = stringResource(R.string.category)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                UlyDropDownTextField(
                    state = uiState.categoryDropState,
                    textBuilder = { it?.name ?: "" },
                    modifier = Modifier.weight(1.8f)
                )

                UlyOutlinedButton(
                    onClick = { navigateToAddCategory() },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(horizontal = UlyTheme.spacing.mediumSmall)
                ) {
                    Text(text = stringResource(R.string.add_new_category))
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
                    textBuilder = { it?.name ?: "" },
                    modifier = Modifier.weight(1.8f)
                )

                UlyOutlinedButton(
                    onClick = { navigateToAddCompany() },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(horizontal = UlyTheme.spacing.mediumSmall)
                ) {
                    Text(text = stringResource(R.string.add_new_company))
                }
            }
        }

        VerticalSpacer(UlyTheme.spacing.large)

        TextInputItem(title = stringResource(R.string.due_date)) {
            var showModal by remember { mutableStateOf(false) }
            val formatter = remember { SimpleDateFormat("dd/MM/YYYY") }

            OutlinedTextField(
                value = uiState.dueDate?.let { formatter.format(uiState.dueDate) } ?: "",
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("DD/MM/YYYY") },
                trailingIcon = {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select date")
                },
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .pointerInput(uiState.dueDate) {
                        awaitEachGesture {
                            awaitFirstDown(pass = PointerEventPass.Initial)
                            val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                            if (upEvent != null) {
                                showModal = true
                            }
                        }
                    }
            )

            if (showModal) {
                DatePickerModal(
                    onDateSelected = { timeMils ->
                        viewModel.updateDueDate(timeMils)
                    },
                    onDismiss = { showModal = false },
                    initialDate = uiState.dueDate
                )
            }
        }

        VerticalSpacer(UlyTheme.spacing.large)

        TextInputItem(title = "Repeat") {
            SingleChoiceSegmentedButtonRow {
                val selectedButton = uiState.repeat

                StartSegmentedButton(
                    selected = selectedButton == UtilityRepeat.WEEKLY,
                    onClick = { viewModel.updateRepeat(UtilityRepeat.WEEKLY) },
                    text = stringResource(R.string.weekly)
                )

                CenterSegmentedButton(
                    selected = selectedButton == UtilityRepeat.MONTHLY,
                    onClick = { viewModel.updateRepeat(UtilityRepeat.MONTHLY) },
                    text = stringResource(R.string.monthly)
                )

                EndSegmentedButton(
                    selected = selectedButton == UtilityRepeat.ANNUALLY,
                    onClick = { viewModel.updateRepeat(UtilityRepeat.ANNUALLY) },
                    text = stringResource(R.string.annual)
                )
            }
        }

        VerticalSpacer(UlyTheme.spacing.large)

        TextInputItem(title = stringResource(R.string.amount_to_pay)) {
            UlyOutlinedTextFiled(
                state = uiState.amount,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.AttachMoney, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
            )
        }

        uiState.categoryParameters.forEach { parameter ->
            val textFieldState = rememberTextFieldState(parameter.name)
            VerticalSpacer(UlyTheme.spacing.large)
            TextInputItem(title = parameter.name) {
                UlyOutlinedTextFiled(
                    state = textFieldState
                )
            }
        }

        VerticalSpacer(400.dp)
    }
}
