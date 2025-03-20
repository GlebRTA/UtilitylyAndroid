package com.gvituskins.utilityly.presentation.components.containers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gvituskins.utilityly.presentation.components.buttons.UlyFilledTonalButton
import com.gvituskins.utilityly.presentation.components.topAppBars.UlyDefaultTopAppBar
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@Composable
fun ManageContainer(
    navigateBack: (() -> Unit)?,
    titleText: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    isButtonEnabled: Boolean = true,
    contentPaddings: PaddingValues = PaddingValues(UlyTheme.spacing.mediumSmall),
    content: @Composable ColumnScope.() -> Unit,
) {
    UlyScaffold(
        topBar = {
            UlyDefaultTopAppBar(
                title = titleText,
                navigateBack = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
                    .padding(contentPaddings)
            ) {
                content()
            }

            UlyFilledTonalButton(
                onClick = onButtonClick,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = UlyTheme.spacing.mediumSmall),
                enabled = isButtonEnabled
            ) {
                Text(text = buttonText)
            }
        }
    }
}
