package com.gvituskins.utilityly.presentation.screens.main.utilities.grid

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.presentation.screens.main.utilities.components.UtilityGridCard
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@Composable
fun UtilitiesGridScreen(
    viewModel: UtilitiesGridViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
        ) {
            items(13) {
                UtilityGridCard(
                    modifier = Modifier
                        .padding(UlyTheme.spacing.xSmall)
                        .clickable {  }
                )
            }
        }
    }
}
