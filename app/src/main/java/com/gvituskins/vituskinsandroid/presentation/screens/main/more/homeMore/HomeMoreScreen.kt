package com.gvituskins.vituskinsandroid.presentation.screens.main.more.homeMore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gvituskins.vituskinsandroid.BuildConfig
import com.gvituskins.vituskinsandroid.presentation.theme.UhTheme
import com.gvituskins.vituskinsandroid.presentation.views.UhScaffold
import com.gvituskins.vituskinsandroid.presentation.views.topAppBars.UhDefaultTopAppBar

@Composable
fun HomeMoreScreen(
    navigateToSettings: () -> Unit,
) {
    UhScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { UhDefaultTopAppBar(title = "More") }
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
        ) {
            ListItem(
                headlineContent = { Text(text = "Settings") },
                modifier = Modifier.clickable {
                    navigateToSettings()
                },
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

            if (BuildConfig.DEBUG) {
                ListItem(
                    headlineContent = { Text(text = "Testing (DEBUG only)") },
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

                Text(
                    text = "Version: ${BuildConfig.VERSION_NAME} build ${BuildConfig.VERSION_CODE}",
                    modifier = Modifier.padding(UhTheme.spacing.small),
                    style = UhTheme.typography.bodyLarge
                )
            }
        }
    }
}