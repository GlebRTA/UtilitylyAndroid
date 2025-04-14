package com.gvituskins.utilityly.presentation.screens.main.companies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.presentation.components.containers.UlyScaffold
import com.gvituskins.utilityly.presentation.components.dialogs.UlyAlertDialog
import com.gvituskins.utilityly.presentation.components.topAppBars.UlyDefaultTopAppBar

@Composable
fun CompaniesScreen(
    navigateBack: () -> Unit,
    navigateToAddCompany: () -> Unit,
    navigateToEditCompany: (Int) -> Unit,
    viewModel: CompaniesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UlyScaffold(
        topBar = {
            UlyDefaultTopAppBar(
                title = "Companies",
                navigateBack = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToAddCompany) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new company"
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
            items(items = uiState.companies, key = { it.id }) { company ->
                CompanyListItem(
                    name = company.name,
                    onClick = { navigateToEditCompany(company.id) },
                    onDeleteClick = { viewModel.updateModal(CompanyModal.Delete(company)) }
                )
            }
        }
    }

    when (val modalInfo = uiState.currentModal) {
        is CompanyModal.Delete -> {
            UlyAlertDialog(
                titleText = "Do you want to delete company: ${modalInfo.company.name}?",
                onDismissRequest = {
                    viewModel.updateModal(CompanyModal.None)
                },
                confirmText = "Delete",
                onConfirmClicked = {
                    viewModel.deleteCompany(modalInfo.company)
                },
                dismissText = "Cancel",
            )
        }

        CompanyModal.None -> {}
    }
}

@Composable
private fun LazyItemScope.CompanyListItem(
    name: String,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(modifier = Modifier.animateItem()) {
        ListItem(
            headlineContent = { Text(text = name) },
            modifier = Modifier.clickable { onClick() },
            trailingContent = {
                IconButton(
                    onClick = { onDeleteClick() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete Company"
                    )
                }
            }
        )

        HorizontalDivider()
    }
}
