package com.gvituskins.utilityly.presentation.screens.main.categories.manageCategory

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gvituskins.utilityly.R
import com.gvituskins.utilityly.presentation.components.UlyScaffold
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.buttons.UlyFilledTonalButton
import com.gvituskins.utilityly.presentation.components.inputItems.TextFieldInputItem
import com.gvituskins.utilityly.presentation.components.inputItems.TextInputItem
import com.gvituskins.utilityly.presentation.components.modalBottomSheet.UlyModalBottomSheet
import com.gvituskins.utilityly.presentation.components.textFields.UlyOutlinedTextFiled
import com.gvituskins.utilityly.presentation.components.topAppBars.UlyDefaultTopAppBar
import com.gvituskins.utilityly.presentation.theme.UlyTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCategoryScreen(
    navigateBack: () -> Unit,
    viewModel: ManageCategoryViewModel = hiltViewModel()
) {
    var showm3 by remember {
        mutableStateOf(false)
    }
    UlyScaffold(
        topBar = {
            UlyDefaultTopAppBar(
                title = stringResource(R.string.title_add_category),
                navigateBack = navigateBack
            )
        }
    ) { innerPadding ->
        val b = rememberTextFieldState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
                    .padding(UlyTheme.spacing.mediumSmall)
            ) {
                TextFieldInputItem(
                    title = "Name",
                    textFiledState = b,
                    placeholderText = "Add Category Name"
                )

                VerticalSpacer(UlyTheme.spacing.mediumLarge)

                TextFieldInputItem(
                    title = "Description",
                    textFiledState = b,
                    placeholderText = "Add Category Description",
                    lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 3)
                )

                VerticalSpacer(UlyTheme.spacing.mediumLarge)

                TextInputItem(title = "Parameters") {
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .border(
                                1.dp,
                                UlyTheme.colors.primary.copy(alpha = .4f),
                                UlyTheme.shapes.small
                            )
                            .clickable {
                                showm3 = true
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Add Icon")
                    }
                }

                VerticalSpacer(UlyTheme.spacing.mediumLarge)

                TextInputItem(title = "Parameters") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(UlyTheme.spacing.xSmall),
                        modifier = Modifier.width(IntrinsicSize.Min)
                    ) {
                        UlyOutlinedTextFiled(
                            state = b,
                            label = {
                                Text(text = "Kitchen Hot Water")
                            },
                            trailingIcon = {
                                IconButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null
                                    )
                                }
                            }
                        )

                        UlyOutlinedTextFiled(
                            state = b,
                            label = {
                                Text(text = "Kitchen Cold Water")
                            },
                            trailingIcon = {
                                IconButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null
                                    )
                                }
                            }
                        )

                        UlyFilledTonalButton(
                            onClick = {},
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Add Parameter")
                        }
                    }
                }

                VerticalSpacer(ButtonDefaults.MinHeight * 2)
            }

            UlyFilledTonalButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = UlyTheme.spacing.mediumSmall),
            ) {
                Text(text = "Add")
            }
        }
    }

    if (showm3) {
        UlyModalBottomSheet(onDismissRequest = { showm3 = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {

                repeat(10) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        repeat(3) {
                            Image(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clip(UlyTheme.shapes.medium)
                                    .clickable { }
                            )
                        }
                    }
                }

                Spacer(Modifier.navigationBarsPadding())
            }
        }
    }
}
