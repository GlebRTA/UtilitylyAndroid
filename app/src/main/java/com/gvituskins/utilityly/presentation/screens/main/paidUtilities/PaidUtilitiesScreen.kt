package com.gvituskins.utilityly.presentation.screens.main.paidUtilities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import com.gvituskins.utilityly.presentation.components.UlyScaffold
import com.gvituskins.utilityly.presentation.components.listItems.UlyUtilityListItem
import com.gvituskins.utilityly.presentation.components.topAppBars.UlyDefaultTopAppBar

@Composable
fun PaidUtilitiesScreen(
    navigateToUtilityDetails: (Int) -> Unit,
    viewModel: PaidUtilitiesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UlyScaffold(
        topBar = { UlyDefaultTopAppBar(title = "Paid Utilities") },
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
                        onStartBtnClicked = { navigateToUtilityDetails(utility.id) },
                        onEndBtnClicked = {
                            viewModel.update(PaidUtilityEvent.ChangePaidStatus(utility.id))
                        },
                        endBtnText = "Unpaid",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(UlyTheme.spacing.small)
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
