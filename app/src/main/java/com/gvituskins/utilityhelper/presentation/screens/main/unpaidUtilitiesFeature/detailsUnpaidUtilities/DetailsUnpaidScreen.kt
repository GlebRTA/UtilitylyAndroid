package com.gvituskins.utilityhelper.presentation.screens.main.unpaidUtilitiesFeature.detailsUnpaidUtilities

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
import com.gvituskins.utilityhelper.presentation.theme.UhTheme
import com.gvituskins.utilityhelper.presentation.views.topAppBars.UhDefaultTopAppBar

@Composable
fun DetailsUnpaidScreen(
    navigateBack: () -> Unit,
    viewModel: DetailsUnpaidViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            UhDefaultTopAppBar(
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
                .padding(UhTheme.spacing.mediumSmall)
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
