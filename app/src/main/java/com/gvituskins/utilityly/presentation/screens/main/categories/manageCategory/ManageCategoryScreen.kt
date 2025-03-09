package com.gvituskins.utilityly.presentation.screens.main.categories.manageCategory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.gvituskins.utilityly.R
import com.gvituskins.utilityly.presentation.components.UlyScaffold
import com.gvituskins.utilityly.presentation.components.topAppBars.UlyDefaultTopAppBar
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCategoryScreen(
    navigateBack: () -> Unit,
    viewModel: ManageCategoryViewModel = hiltViewModel()
) {
    UlyScaffold(
        topBar = {
            UlyDefaultTopAppBar(
                title = stringResource(R.string.title_add_category),
                navigateBack = navigateBack
            )
        }
    ) { innerPadding ->

        var a by remember {
            mutableStateOf("")
        }

        val b = rememberTextFieldState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column {
                Text(text = "Ads")

                BasicTextField(
                    state = b,
                    decorator = {

                    }
                )

                OutlinedTextField(
                    value = a,
                    onValueChange = {
                        a = it
                    }
                )
            }

            FilledTonalButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = UlyTheme.spacing.mediumSmall),
                shape = UlyTheme.shapes.small,
            ) {
                Text(text = "Add")
            }

        }


    }
}
