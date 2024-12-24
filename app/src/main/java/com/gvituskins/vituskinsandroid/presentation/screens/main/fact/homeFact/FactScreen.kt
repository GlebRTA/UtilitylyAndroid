package com.gvituskins.vituskinsandroid.presentation.screens.main.fact.homeFact

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.vituskinsandroid.presentation.theme.UhTheme
import com.gvituskins.vituskinsandroid.presentation.views.UhScaffold
import com.gvituskins.vituskinsandroid.presentation.views.topAppBars.UhDefaultTopAppBar

@Composable
fun FactScreen(
    viewModel: FactViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UhScaffold(
        topBar = { UhDefaultTopAppBar(title = "Random fact from internet") }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (uiState.error != null) {
                    Text(text = uiState.error ?: "Error")
                } else {
                    Text(
                        text = uiState.facts?.fact ?: "Error. Try Again",
                        modifier = Modifier.padding(UhTheme.spacing.mediumSmall),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(Modifier.weight(1f))
                Button(
                    onClick = { viewModel.getRandomFact() },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Text(text = "Get New Fact")
                }
            }
        }
    }
}