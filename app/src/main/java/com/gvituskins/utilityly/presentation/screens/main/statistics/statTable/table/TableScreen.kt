package com.gvituskins.utilityly.presentation.screens.main.statistics.statTable.table

import android.content.pm.ActivityInfo
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@Composable
fun TableScreen(
    navigateBack: () -> Unit,
    viewModel: TableViewModel = hiltViewModel()
) {
    val activity = LocalActivity.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(UlyTheme.colors.background),
        contentPadding = WindowInsets.safeDrawing.asPaddingValues()
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = uiState.year.toString(),
                    modifier = Modifier.align(Alignment.Center),
                    style = UlyTheme.typography.titleLarge,
                )

                IconButton(
                    onClick = navigateBack,
                    modifier = Modifier.align(Alignment.CenterEnd),
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close table")
                }
            }
        }

        stickyHeader {
            TableRow(
                categoryName = "",
                months = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"),
                modifier = Modifier.height(100.dp)
            )
        }

        items(items = uiState.tableRows) { row ->
            TableRow(
                categoryName = row.name,
                months = row.months,
                modifier = Modifier.height(100.dp)
            )
        }
    }
}

@Composable
private fun TableRow(
    categoryName: String,
    months: List<String>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        TableRowCell(value = categoryName)

        months.forEach { value ->
            TableRowCell(value = value)
        }
    }
}

@Composable
private fun RowScope.TableRowCell(value: String) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .background(UlyTheme.colors.background)
            .border(1.dp, UlyTheme.colors.outline)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicText(
            text = value,
            maxLines = 1,
            autoSize = TextAutoSize.StepBased(
                minFontSize = 8.sp,
                maxFontSize = 16.sp
            ),
            style = UlyTheme.typography.bodySmall.copy(color = UlyTheme.colors.onBackground),
        )
    }
}
