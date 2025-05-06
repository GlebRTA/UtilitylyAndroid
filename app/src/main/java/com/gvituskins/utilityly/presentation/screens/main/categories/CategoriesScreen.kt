package com.gvituskins.utilityly.presentation.screens.main.categories

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.R
import com.gvituskins.utilityly.presentation.components.CategoryColorBox
import com.gvituskins.utilityly.presentation.components.HorizontalSpacer
import com.gvituskins.utilityly.presentation.components.VerticalSpacer
import com.gvituskins.utilityly.presentation.components.containers.UlyScaffold
import com.gvituskins.utilityly.presentation.components.dialogs.UlyAlertDialog
import com.gvituskins.utilityly.presentation.components.navBar.UlyBottomNavigationBar
import com.gvituskins.utilityly.presentation.components.stubs.EmptyStub
import com.gvituskins.utilityly.presentation.components.topAppBars.UlyDefaultTopAppBar
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@Composable
fun CategoriesScreen(
    navigateToAddCategory: () -> Unit,
    navigateToEditCategory: (Int) -> Unit,
    viewModel: CategoriesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val lazyListState = rememberLazyListState()
    val showFab by remember {
        derivedStateOf {
            lazyListState.lastScrolledBackward || !lazyListState.canScrollBackward
        }
    }

    UlyScaffold(
        topBar = { UlyDefaultTopAppBar(title = stringResource(R.string.nav_categories)) },
        bottomBar = { UlyBottomNavigationBar() },
        floatingActionButton = {
            AnimatedVisibility(
                visible = showFab,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FloatingActionButton(onClick = navigateToAddCategory) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_new_category)
                    )
                }
            }
        }
    ) { innerPadding ->
        if (uiState.categories.isEmpty()) {
            EmptyStub(
                text = stringResource(R.string.there_is_no_category_added),
                paddings = innerPadding
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding,
                state = lazyListState
            ) {
                items(items = uiState.categories, key = { it.id }) { category ->
                    CategoryItem(
                        title = category.name,
                        description = category.description,
                        color = category.color,
                        onDeleteClick = { viewModel.updateDeleteDialogVisibility(category)  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clickable { navigateToEditCategory(category.id) }
                            .padding(
                                horizontal = UlyTheme.spacing.small,
                                vertical = UlyTheme.spacing.xSmall
                            )
                    )
                    HorizontalDivider()
                }

                item { VerticalSpacer(UlyTheme.spacing.mediumLarge) }
            }
        }
    }

    uiState.deleteCategory?.let { categoryToDelete ->
        UlyAlertDialog(
            onDismissRequest = {
                viewModel.updateDeleteDialogVisibility(null)
            },
            titleText = "Do you want delete category ${categoryToDelete.name}?",
            confirmText = stringResource(R.string.delete),
            onConfirmClicked = {
                viewModel.deleteCategory(categoryToDelete)
            },
            dismissText = stringResource(R.string.cancel),
        )
    }
}

@Composable
private fun CategoryItem(
    title: String,
    description: String?,
    color: Color,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryColorBox(
            size = 70.dp,
            color = color
        )

        HorizontalSpacer(UlyTheme.spacing.small)

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    style = UlyTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete_category)
                    )
                }
            }

            description?.let {
                Text(
                    text = description,
                    style = UlyTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
