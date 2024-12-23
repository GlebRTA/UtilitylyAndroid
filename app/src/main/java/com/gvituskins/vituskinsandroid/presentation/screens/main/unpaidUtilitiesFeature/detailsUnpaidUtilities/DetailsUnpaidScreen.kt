package com.gvituskins.vituskinsandroid.presentation.screens.main.unpaidUtilitiesFeature.detailsUnpaidUtilities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.vituskinsandroid.presentation.views.topAppBars.UhDefaultTopAppBar

@Composable
fun DetailsUnpaidScreen(
    navigateBack: () -> Unit,
    viewModel: DetailsUnpaidViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { UhDefaultTopAppBar(title = "Utility Details", navigateBack = navigateBack) }
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
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
        }
    }
}
