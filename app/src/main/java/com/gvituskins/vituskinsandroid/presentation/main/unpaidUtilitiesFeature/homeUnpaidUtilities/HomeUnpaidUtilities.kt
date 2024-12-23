package com.gvituskins.vituskinsandroid.presentation.main.unpaidUtilitiesFeature.homeUnpaidUtilities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.vituskinsandroid.presentation.views.UhScaffold
import com.gvituskins.vituskinsandroid.presentation.views.listItems.UhUtilityListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUnpaidUtilitiesScreen(
    navigateToUtilityDetails: (Int) -> Unit,
    viewModel: HomeUnpaidUtilitiesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UhScaffold(
        topBar = { TopAppBar(title = { Text(text = "Unpaid Utilities") }) },
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
            items(items = uiState.utilities) { utility ->
                UhUtilityListItem(
                    primaryText = utility.name,
                    descriptionText = utility.description,
                    onStartBtnClicked = {
                        utility.id?.let(navigateToUtilityDetails)
                    },
                    onEndBtnClicked = {
                        if (utility.id != null) {
                            viewModel.update(HomeUnpaidEvent.ChangePaidStatus(utility.id))
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                HorizontalDivider()
            }
        }
    }

    if (uiState.showAddNewBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.update(HomeUnpaidEvent.ChangeAddNewBS(false)) }
        ) {
            var name by remember { mutableStateOf("") }
            var description by remember { mutableStateOf("") }

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

                Button(
                    onClick = {
                        viewModel.update(
                            HomeUnpaidEvent.CreateNewUtility(
                                name = name,
                                description = description
                            )
                        )
                        viewModel.update(HomeUnpaidEvent.ChangeAddNewBS(false))
                    },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Text(text = "Create")
                }
            }
        }
    }
}
