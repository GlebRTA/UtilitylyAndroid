package com.gvituskins.vituskinsandroid.presentation.main.unpaidUtilitiesFeature.homeUnpaidUtilities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.material3.Scaffold
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUnpaidUtilitiesScreen(
    navigateToUtilityDetails: (Int) -> Unit,
    viewModel: HomeUnpaidUtilitiesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showAddNewBottomSheet by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Unpaid Utilities") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddNewBottomSheet = true }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new utility")
            }
        },
        contentWindowInsets = WindowInsets(0,0,0,0)
    ) { innerPaddings ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
        ) {
            items(items = uiState.utilities) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 40.dp)
                        .clickable {
                            if (it.id != null) {
                                navigateToUtilityDetails(it.id)
                            }
                        },
                ) {
                    Text(text = it.name)
                    it.description?.let { description ->
                        Text(text = description)
                    }
                }
                HorizontalDivider()
            }
        }
    }

    if (showAddNewBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showAddNewBottomSheet = false }
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
                        showAddNewBottomSheet = false
                    },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Text(text = "Create")
                }
            }
        }
    }
}
