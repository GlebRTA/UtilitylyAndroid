package com.gvituskins.utilityly.presentation.screens.main.categories.manageCategory

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.R
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.buttons.UlyFilledTonalButton
import com.gvituskins.utilityly.presentation.components.containers.ManageContainer
import com.gvituskins.utilityly.presentation.components.inputItems.TextFieldInputItem
import com.gvituskins.utilityly.presentation.components.inputItems.TextInputItem
import com.gvituskins.utilityly.presentation.components.modalBottomSheet.UlyModalBottomSheet
import com.gvituskins.utilityly.presentation.components.textFields.UlyOutlinedTextFiled
import com.gvituskins.utilityly.presentation.core.utils.collectAsOneTimeEvent
import com.gvituskins.utilityly.presentation.theme.UlyTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCategoryScreen(
    navigateBack: () -> Unit,
    viewModel: ManageCategoryViewModel = hiltViewModel(),
) {
    var showm3 by remember {
        mutableStateOf(false)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.label.collectAsOneTimeEvent { event ->
        when (event) {
            ManageCategoryOneTime.NavigateBack -> navigateBack()
        }
    }

    ManageContainer(
        navigateBack = navigateBack,
        titleText = stringResource(if (uiState.isAddMode) R.string.title_add_category else R.string.title_edit_category),
        buttonText = stringResource(if (uiState.isAddMode) R.string.add else R.string.edit),
        onButtonClick = { viewModel.manageCategory() },
        isButtonEnabled = uiState.isValidToManage
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(UlyTheme.spacing.mediumLarge)
        ) {
            TextFieldInputItem(
                title = stringResource(R.string.name),
                textFiledState = uiState.name,
                placeholderText = stringResource(R.string.category_name),
                isError = uiState.nameIsError,
                errorText = "Category name should not be empty"
            )

            TextFieldInputItem(
                title = stringResource(R.string.description),
                textFiledState = uiState.description,
                placeholderText = stringResource(R.string.category_description),
                lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 3)
            )

            TextInputItem(title = stringResource(R.string.icon)) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .border(
                            UlyTheme.spacing.border,
                            UlyTheme.colors.outline,
                            UlyTheme.shapes.small
                        )
                        .clickable {
                            showm3 = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.category_icon))
                }
            }


            TextInputItem(title = stringResource(R.string.parameters)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(UlyTheme.spacing.xSmall),
                    modifier = Modifier.width(IntrinsicSize.Min)
                ) {
                    uiState.localParameters.forEachIndexed { index, parameter ->
                        UlyOutlinedTextFiled(
                            state = TextFieldState(),
                            readOnly = true,
                            placeholder = { Text(text = parameter.name) },
                            trailingIcon = {
                                Row {
                                    IconButton(
                                        onClick = {
                                            viewModel.updateParameterSheet(
                                                newState = ManageCategoryModal.Parameter(parameter)
                                            )
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = null
                                        )
                                    }

                                    IconButton(onClick = { viewModel.deleteLocalParameter(index) }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = null
                                        )
                                    }
                                }
                            },
                        )
                    }

                    UlyFilledTonalButton(
                        onClick = { viewModel.updateParameterSheet(ManageCategoryModal.Parameter(null)) },
                        modifier = Modifier
                            .widthIn(min = 220.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = stringResource(R.string.add_parameter))
                    }
                }
            }
        }

        VerticalSpacer(ButtonDefaults.MinHeight * 2)
    }

    when (val modalInfo = uiState.currentModal) {
        is ManageCategoryModal.Parameter -> {
            UlyModalBottomSheet(
                onDismissRequest = { viewModel.updateParameterSheet(ManageCategoryModal.None) }
            ) {
                val name = rememberTextFieldState(modalInfo.parameter?.name ?: "")

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = UlyTheme.spacing.mediumSmall)
                ) {
                    TextFieldInputItem(
                        title = "Name",
                        textFiledState = name,
                        isError = name.text.isEmpty(),
                        errorText = "Parameter name should not be empty"
                    )

                    VerticalSpacer(UlyTheme.spacing.xxLarge)
                    UlyFilledTonalButton(
                        onClick = {
                            if (modalInfo.parameter != null) {
                                viewModel.editLocalParameter(
                                    parameter = modalInfo.parameter,
                                    newName = name.text.toString()
                                )
                            } else {
                                viewModel.addLocalParameter(name.text.toString())
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = stringResource(if (modalInfo.parameter != null) R.string.add else R.string.edit))
                    }
                }

            }
        }
        ManageCategoryModal.None -> {  }
    }

    if (showm3) {
        UlyModalBottomSheet(onDismissRequest = { showm3 = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {

                repeat(10) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        repeat(3) {
                            Image(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clip(UlyTheme.shapes.medium)
                                    .clickable { }
                            )
                        }
                    }
                }

                Spacer(Modifier.navigationBarsPadding())
            }
        }
    }
}
