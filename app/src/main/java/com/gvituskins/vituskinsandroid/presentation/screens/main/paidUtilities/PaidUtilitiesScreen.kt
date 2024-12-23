package com.gvituskins.vituskinsandroid.presentation.screens.main.paidUtilities

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
import com.gvituskins.vituskinsandroid.presentation.theme.UhTheme
import com.gvituskins.vituskinsandroid.presentation.views.UhScaffold
import com.gvituskins.vituskinsandroid.presentation.views.listItems.UhUtilityListItem
import com.gvituskins.vituskinsandroid.presentation.views.topAppBars.UhDefaultTopAppBar

@Composable
fun PaidUtilitiesScreen(
    navigateToUtilityDetails: (Int) -> Unit,
    viewModel: PaidUtilitiesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UhScaffold(
        topBar = { UhDefaultTopAppBar(title = "Paid Utilities") },
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
                    onStartBtnClicked = { utility.id?.let(navigateToUtilityDetails) },
                    onEndBtnClicked = {
                        utility.id?.let {
                            viewModel.update(PaidUtilityEvent.ChangePaidStatus(it))
                        }
                    },
                    endBtnText = "Unpaid",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(UhTheme.spacing.small)
                )
                HorizontalDivider()
            }
        }
    }
}
