package com.gvituskins.vituskinsandroid.presentation.main.more.homeMore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gvituskins.vituskinsandroid.presentation.views.UhScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMoreScreen() {
    UhScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = "More") }) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            ListItem(
                headlineContent = { Text(text = "Settings") },
                modifier = Modifier.clickable {  },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings",
                    )
                },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Settings",
                    )
                }
            )
            HorizontalDivider()

            ListItem(
                headlineContent = { Text(text = "Testing") },
                modifier = Modifier.clickable {  },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Filled.Build,
                        contentDescription = "For Testing",
                    )
                },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "For Testing",
                    )
                }
            )
            HorizontalDivider()
        }
    }
}