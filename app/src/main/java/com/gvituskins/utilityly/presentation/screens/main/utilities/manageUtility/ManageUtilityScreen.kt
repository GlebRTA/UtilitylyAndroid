package com.gvituskins.utilityly.presentation.screens.main.utilities.manageUtility

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.presentation.components.containers.ManageContainer
import com.gvituskins.utilityly.presentation.components.inputItems.TextInputItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageUtilityScreen(
    navigateBack: () -> Unit,
    viewModel: ManageUtilityViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    ManageContainer(
        navigateBack = navigateBack,
        titleText = "Edit Utility",
        buttonText = "Edit",
        onButtonClick = {}
    ) {
        var isCategoryExpanded by remember {
            mutableStateOf(false)
        }

        var categoryValue by remember {
            mutableStateOf("tet")
        }

        TextInputItem(title = "Category") {
            ExposedDropdownMenuBox(
                expanded = isCategoryExpanded,
                onExpandedChange = { isCategoryExpanded = it }
            ) {
                OutlinedTextField(
                    modifier = Modifier.menuAnchor(
                        type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                    ),
                    value = categoryValue,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryExpanded)
                    },
                )

                ExposedDropdownMenu(
                    expanded = isCategoryExpanded,
                    onDismissRequest = { isCategoryExpanded = false },
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Electrum") },
                        onClick = {
                            categoryValue = "Electrum"
                            isCategoryExpanded = false
                        },
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Tet") },
                        onClick = {
                            categoryValue = "Tet"
                            isCategoryExpanded = false
                        }
                    )
                }
            }
        }
    }
}