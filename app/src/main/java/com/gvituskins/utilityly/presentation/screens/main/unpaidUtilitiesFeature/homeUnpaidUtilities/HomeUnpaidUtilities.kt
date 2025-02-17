package com.gvituskins.utilityly.presentation.screens.main.unpaidUtilitiesFeature.homeUnpaidUtilities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import com.gvituskins.utilityly.presentation.views.UlyScaffold
import com.gvituskins.utilityly.presentation.views.listItems.UlyUtilityListItem
import com.gvituskins.utilityly.presentation.views.topAppBars.UlyDefaultTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUnpaidUtilitiesScreen(
    navigateToUtilityDetails: (Int) -> Unit,
    viewModel: HomeUnpaidUtilitiesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UlyScaffold(
        topBar = { UlyDefaultTopAppBar(title = "Unpaid Utilities") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.update(HomeUnpaidEvent.ChangeAddNewBS(true)) }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new utility")
            }
        }
    ) { innerPaddings ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
        ) {
            items(items = uiState.utilities, key = { it.id }) { utility ->
                Column(modifier = Modifier.animateItem()) {
                    UlyUtilityListItem(
                        primaryText = utility.name,
                        descriptionText = utility.description,
                        onStartBtnClicked = {
                            navigateToUtilityDetails(utility.id)
                        },
                        onEndBtnClicked = {
                            viewModel.update(HomeUnpaidEvent.ChangePaidStatus(utility.id))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(UlyTheme.spacing.small)
                    )
                    HorizontalDivider()
                }
            }

            item {
                Spacer(modifier = Modifier.height(70.dp))
            }
        }
    }

    if (uiState.showAddNewBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.update(HomeUnpaidEvent.ChangeAddNewBS(false)) }
        ) {
            var name by rememberSaveable { mutableStateOf("") }
            var description by rememberSaveable { mutableStateOf("") }
            var amount by rememberSaveable { mutableStateOf("") }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Utility Bill Name") }
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(text = "Description") }
                )

                OutlinedTextField(
                    value = amount,
                    onValueChange = {
                        if (it.isDigitsOnly() || (it.contains(".") && it.count { it == '.' } <= 1)) {
                            amount = it
                        }
                    },
                    maxLines = 1,
                    label = { Text(text = "Amount") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Button(
                    onClick = {
                        viewModel.update(
                            HomeUnpaidEvent.CreateNewUtility(
                                name = name,
                                description = description,
                                amount = amount
                            )
                        )
                        viewModel.update(HomeUnpaidEvent.ChangeAddNewBS(false))
                    },
                    enabled = name.isNotEmpty() && description.isNotEmpty() && amount.toDoubleOrNull() != null,
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Text(text = "Create")
                }
            }
        }
    }
}
