package com.gvituskins.utilityly.presentation.screens.main.unpaidUtilitiesFeature.detailsUnpaidUtilities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import com.gvituskins.utilityly.presentation.views.topAppBars.UlyDefaultTopAppBar

@Composable
fun DetailsUnpaidScreen(
    navigateBack: () -> Unit,
    viewModel: DetailsUnpaidViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            UlyDefaultTopAppBar(
                title = "Utility Details",
                navigateBack = navigateBack,
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.delete()
                            navigateBack()
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Utility")
                    }
                }
            )
        }
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
                .padding(UlyTheme.spacing.mediumSmall)
        ) {
            uiState.utility?.id?.let {
                Text(text = "Id = $it")
            }

            uiState.utility?.name?.let {
                Text(text = "Name = $it")
            }

            uiState.utility?.description?.let {
                Text(text = "Description = $it")
            }

            uiState.utility?.date?.let {
                Text(text = "Date = $it")
            }

            uiState.utility?.isPaid?.let {
                Text(text = "isPaid = $it")
            }

            uiState.utility?.amount?.let {
                Text(text = "Amount = $it")
            }
        }
    }
}
