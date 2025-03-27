package com.gvituskins.utilityly.presentation.screens.main.more

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.BuildConfig
import com.gvituskins.utilityly.R
import com.gvituskins.utilityly.domain.models.enums.ThemeType
import com.gvituskins.utilityly.presentation.components.containers.UlyScaffold
import com.gvituskins.utilityly.presentation.components.navBar.UlyBottomNavigationBar
import com.gvituskins.utilityly.presentation.components.sections.SettingsOpenableSection
import com.gvituskins.utilityly.presentation.components.topAppBars.UlyDefaultTopAppBar
import com.gvituskins.utilityly.presentation.theme.UlyTheme

@Composable
fun MoreScreen(
    navigateToLocations: () -> Unit,
    navigateToCompanies: () -> Unit,
    viewModel: MoreViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UlyScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { UlyDefaultTopAppBar(title = stringResource(R.string.nav_more)) },
        bottomBar = { UlyBottomNavigationBar() },
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
        ) {
            SettingsOpenableSection(
                title = stringResource(R.string.app_theme),
                isOpened = uiState.themSectionOpened,
                onOpen = { isOpened ->
                    viewModel.changeThemeSection(isOpened)
                },
                modifier = Modifier.background(UlyTheme.colors.surface)
            ) {
                ThemeSettings(
                    themeType = uiState.theme,
                    onThemeChange = { newType ->
                        viewModel.updateTheme(newType)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(UlyTheme.spacing.mediumSmall)
                )
            }

            ListItem(
                headlineContent = { Text(text = stringResource(R.string.locations)) },
                modifier = Modifier.clickable { navigateToLocations() },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Filled.LocationCity,
                        contentDescription = stringResource(R.string.locations),
                    )
                },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(R.string.locations),
                    )
                }
            )
            HorizontalDivider()

            ListItem(
                headlineContent = { Text(text = stringResource(R.string.companies)) },
                modifier = Modifier.clickable { navigateToCompanies() },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Filled.Work,
                        contentDescription = stringResource(R.string.companies),
                    )
                },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(R.string.companies),
                    )
                }
            )
            HorizontalDivider()

            if (BuildConfig.DEBUG) {
                Text(
                    text = "Version: ${BuildConfig.VERSION_NAME} build ${BuildConfig.VERSION_CODE}",
                    modifier = Modifier.padding(UlyTheme.spacing.small),
                    style = UlyTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun ThemeSettings(
    themeType: ThemeType,
    onThemeChange: (ThemeType) -> Unit,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        SegmentedButton(
            selected = themeType == ThemeType.SYSTEM,
            onClick = { onThemeChange(ThemeType.SYSTEM) },
            shape = UlyTheme.shapes.medium.copy(
                topEnd = CornerSize(0.dp),
                bottomEnd = CornerSize(0.dp)
            )
        ) {
            Text(text = "System")
        }

        SegmentedButton(
            selected = themeType == ThemeType.LIGHT,
            onClick = { onThemeChange(ThemeType.LIGHT) },
            shape = UlyTheme.shapes.none
        ) {
            Text(text = "Light")
        }

        SegmentedButton(
            selected = themeType == ThemeType.DARK,
            onClick = { onThemeChange(ThemeType.DARK) },
            shape = UlyTheme.shapes.medium.copy(
                topStart = CornerSize(0.dp),
                bottomStart = CornerSize(0.dp)
            )
        ) {
            Text(text = "Dark")
        }
    }
}
