package com.gvituskins.utilityly.presentation.screens.main.more.locations

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.R
import com.gvituskins.utilityly.presentation.components.UlyScaffold
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.buttons.UlyFilledTonalButton
import com.gvituskins.utilityly.presentation.components.dialogs.UlyAlertDialog
import com.gvituskins.utilityly.presentation.components.inputItems.TextFieldInputItem
import com.gvituskins.utilityly.presentation.components.modalBottomSheet.ModalBottomSheetContainer
import com.gvituskins.utilityly.presentation.components.modalBottomSheet.UlyModalBottomSheet
import com.gvituskins.utilityly.presentation.components.topAppBars.UlyDefaultTopAppBar
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    navigateBack: () -> Unit,
    viewModel: LocationsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UlyScaffold(
        topBar = {
            UlyDefaultTopAppBar(
                title = "Locations",
                navigateBack = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.updateLocationModal(LocationsModal.Add) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new location"
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
            items(items = uiState.locations, key = { it.id }) { location ->
                val color = animateColorAsState(
                    if (uiState.selectedLocationId == location.id) Color.Yellow.copy(alpha = .4f) else UlyTheme.colors.background
                )

                ListItem(
                    headlineContent = { Text(text = location.name) },
                    modifier = Modifier
                        .clickable {
                            viewModel.changeSelectedLocation(location.id)
                        },
                    trailingContent = {
                        Row {
                            IconButton(
                                onClick = {
                                    viewModel.updateLocationModal(LocationsModal.Edit(location))
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "Edit location"
                                )
                            }

                            IconButton(
                                onClick = {
                                    viewModel.updateLocationModal(LocationsModal.Delete(location))
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete location"
                                )
                            }
                        }
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = color.value
                    )
                )

                HorizontalDivider()
            }

            item { VerticalSpacer(84.dp) }
        }
    }

    when (val modalInfo = uiState.currentModal) {
        is LocationsModal.Add -> {
            UlyModalBottomSheet(
                onDismissRequest = { viewModel.updateLocationModal(LocationsModal.None) }
            ) {
                ManageLocationContent(
                    onApply = { name -> viewModel.addLocation(name) },
                    buttonText = stringResource(R.string.add)
                )
            }
        }

        is LocationsModal.Edit -> {
            UlyModalBottomSheet(
                onDismissRequest = { viewModel.updateLocationModal(LocationsModal.None) }
            ) {
                ManageLocationContent(
                    onApply = { name ->
                        viewModel.updateLocation(modalInfo.location.copy(name = name))
                    },
                    buttonText = stringResource(R.string.edit),
                    initLocationNameField = modalInfo.location.name
                )
            }
        }

        is LocationsModal.Delete -> {
            UlyAlertDialog(
                onDismissRequest = {
                    viewModel.updateLocationModal(LocationsModal.None)
                },
                titleText = "Do you want delete location: ${modalInfo.location.name}?",
                confirmText = "Delete",
                onConfirmClicked = {
                    viewModel.deleteLocation(location = modalInfo.location)
                },
                dismissText = "Cancel"
            )
        }
        is LocationsModal.None -> {}
    }
}

@Composable
private fun ManageLocationContent(
    onApply: (String) -> Unit,
    buttonText: String,
    initLocationNameField: String = ""
) {
    val locationName = remember { TextFieldState(initialText = initLocationNameField) }

    ModalBottomSheetContainer {
        TextFieldInputItem(
            title = "Location Name",
            textFiledState = locationName,
            lineLimits = TextFieldLineLimits.SingleLine
        )

        VerticalSpacer(UlyTheme.spacing.xxLarge)

        UlyFilledTonalButton(
            onClick = { onApply(locationName.text.toString()) },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = buttonText)
        }
    }
}
