package com.gvituskins.utilityly.presentation.screens.main.locations

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.buttons.UlyFilledTonalButton
import com.gvituskins.utilityly.presentation.components.containers.UlyScaffold
import com.gvituskins.utilityly.presentation.components.dialogs.UlyAlertDialog
import com.gvituskins.utilityly.presentation.components.inputItems.TextFieldInputItem
import com.gvituskins.utilityly.presentation.components.modalBottomSheet.ModalBottomSheetContainer
import com.gvituskins.utilityly.presentation.components.modalBottomSheet.UlyModalBottomSheet
import com.gvituskins.utilityly.presentation.components.topAppBars.UlyDefaultTopAppBar
import com.gvituskins.utilityly.presentation.core.utils.collectAsOneTimeEvent
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    navigateBack: () -> Unit,
    viewModel: LocationsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    viewModel.label.collectAsOneTimeEvent { event ->
        when (event) {
            is LocationOTE.ShowSnackbar -> snackbarHostState.showSnackbar(message = event.text)
        }
    }

    UlyScaffold(
        topBar = {
            UlyDefaultTopAppBar(
                title = stringResource(R.string.locations),
                navigateBack = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.updateLocationModal(LocationsModal.Add) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_new_location)
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
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

                LocationListItem(
                    name = location.name,
                    onClick = { viewModel.changeSelectedLocation(location.id) },
                    onEditClick = { viewModel.updateLocationModal(LocationsModal.Edit(location)) },
                    onDeleteClick = { viewModel.updateLocationModal(LocationsModal.Delete(location)) },
                    containerColor = color.value
                )
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
                confirmText = stringResource(R.string.delete),
                onConfirmClicked = {
                    viewModel.deleteLocation(location = modalInfo.location)
                },
                dismissText = stringResource(R.string.cancel),
                bodyText = stringResource(R.string.delete_all_related_utilities)
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
    val isError = locationName.text.isEmpty()

    ModalBottomSheetContainer {
        TextFieldInputItem(
            title = stringResource(R.string.location_name),
            textFiledState = locationName,
            lineLimits = TextFieldLineLimits.SingleLine
        )

        VerticalSpacer(UlyTheme.spacing.xxLarge)

        UlyFilledTonalButton(
            onClick = { onApply(locationName.text.toString()) },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            enabled = !isError
        ) {
            Text(text = buttonText)
        }
    }
}

@Composable
private fun LazyItemScope.LocationListItem(
    name: String,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    containerColor: Color
) {
    Column(modifier = Modifier.animateItem()) {
        ListItem(
            headlineContent = { Text(text = name) },
            modifier = Modifier
                .clickable { onClick() },
            trailingContent = {
                Row {
                    IconButton(onClick = { onEditClick() }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = stringResource(R.string.edit_location)
                        )
                    }

                    IconButton(onClick = { onDeleteClick() }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(R.string.delete_location)
                        )
                    }
                }
            },
            colors = ListItemDefaults.colors(
                containerColor = containerColor
            )
        )

        HorizontalDivider()
    }
}
