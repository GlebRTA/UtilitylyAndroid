package com.gvituskins.utilityly.presentation.screens.main.categories.manageCategory

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.gvituskins.utilityly.R
import com.gvituskins.utilityly.presentation.LocalAnimatedContentScopeScope
import com.gvituskins.utilityly.presentation.LocalSharedTransitionScope
import com.gvituskins.utilityly.presentation.components.BottomButtonSpacer
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.buttons.UlyFilledTonalButton
import com.gvituskins.utilityly.presentation.components.containers.ManageContainer
import com.gvituskins.utilityly.presentation.components.inputItems.TextFieldInputItem
import com.gvituskins.utilityly.presentation.components.inputItems.TextInputItem
import com.gvituskins.utilityly.presentation.components.modalBottomSheet.UlyModalBottomSheet
import com.gvituskins.utilityly.presentation.components.textFields.UlyOutlinedTextFiled
import com.gvituskins.utilityly.presentation.core.utils.SharedTransitionKeys
import com.gvituskins.utilityly.presentation.core.utils.collectAsOneTimeEvent
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun ManageCategoryScreen(
    navigateBack: () -> Unit,
    viewModel: ManageCategoryViewModel = hiltViewModel(),
) {
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

            TextInputItem(title = stringResource(R.string.color)) {
                with(LocalSharedTransitionScope.current) {
                    Box(
                        modifier = Modifier
                            .sharedElement(
                                sharedContentState = rememberSharedContentState(
                                    SharedTransitionKeys.categoryColorBox(viewModel.manageCategory.categoryId)
                                ),
                                animatedVisibilityScope = LocalAnimatedContentScopeScope.current
                            )
                            .size(140.dp)
                            .border(
                                width = UlyTheme.spacing.border,
                                color = if (uiState.colorIsError) UlyTheme.colors.error else UlyTheme.colors.outline,
                                UlyTheme.shapes.small
                            )
                            .clip(UlyTheme.shapes.small)
                            .background(uiState.color ?: UlyTheme.colors.background)
                            .clickable {
                                viewModel.updateParameterSheet(ManageCategoryModal.ColorPicker)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (uiState.colorIsError) {
                            Text(text = stringResource(R.string.category_color))
                        }
                    }
                }
            }

            TextInputItem(title = stringResource(R.string.parameters)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(UlyTheme.spacing.xSmall),
                    modifier = Modifier.width(IntrinsicSize.Min)
                ) {
                    uiState.localParameters.forEachIndexed { index, parameter ->
                        key(parameter.id) {
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

        BottomButtonSpacer()
    }

    when (val modalInfo = uiState.currentModal) {
        is ManageCategoryModal.Parameter -> {
            UlyModalBottomSheet(
                onDismissRequest = { viewModel.updateParameterSheet(ManageCategoryModal.None) }
            ) {
                val name = rememberTextFieldState(modalInfo.parameter?.name ?: "")
                val isError = name.text.isEmpty()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = UlyTheme.spacing.mediumSmall)
                ) {
                    TextFieldInputItem(
                        title = stringResource(R.string.name),
                        textFiledState = name,
                        errorText = stringResource(R.string.parameter_name_should_not_be_empty)
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
                            .align(Alignment.CenterHorizontally),
                        enabled = !isError
                    ) {
                        Text(text = stringResource(if (modalInfo.parameter == null) R.string.add else R.string.edit))
                    }
                }

            }
        }
        is ManageCategoryModal.ColorPicker -> {
            UlyModalBottomSheet(
                onDismissRequest = { viewModel.updateParameterSheet(ManageCategoryModal.None) },
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            ) {
                val initColor = uiState.color ?: Color.Red
                val colorPickerController = rememberColorPickerController()
                var color by remember { mutableStateOf(initColor) }
                var hexCode by remember { mutableStateOf("") }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HsvColorPicker(
                        modifier = Modifier
                            .size(260.dp)
                            .padding(10.dp),
                        controller = colorPickerController,
                        initialColor = initColor,
                        onColorChanged = { envelope ->
                            color = envelope.color
                            hexCode = envelope.hexCode
                        }
                    )

                    AlphaSlider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp)
                            .padding(horizontal = UlyTheme.spacing.mediumSmall),
                        controller = colorPickerController
                    )

                    VerticalSpacer(UlyTheme.spacing.mediumLarge)

                    BrightnessSlider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp)
                            .padding(horizontal = UlyTheme.spacing.mediumSmall),
                        controller = colorPickerController,
                    )

                    Text(
                        text = "#$hexCode",
                        color = UlyTheme.colors.onBackground,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    AlphaTile(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(UlyTheme.shapes.small),
                        controller = colorPickerController,
                    )

                    VerticalSpacer(UlyTheme.spacing.mediumLarge)

                    UlyFilledTonalButton(
                        onClick = { viewModel.updateColor(color) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = UlyTheme.spacing.medium)
                    ) {
                        Text(text = stringResource(R.string.save))
                    }
                }
            }
        }
        ManageCategoryModal.None -> {  }
    }
}
