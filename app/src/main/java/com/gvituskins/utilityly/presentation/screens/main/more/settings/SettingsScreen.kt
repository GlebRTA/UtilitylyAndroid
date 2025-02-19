package com.gvituskins.utilityly.presentation.screens.main.more.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gvituskins.utilityly.domain.models.enums.ThemeType
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import com.gvituskins.utilityly.presentation.components.UlyScaffold
import com.gvituskins.utilityly.presentation.components.sections.SettingsOpenableSection
import com.gvituskins.utilityly.presentation.components.topAppBars.UlyDefaultTopAppBar

@Composable
fun SettingsScreen(
    navigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UlyScaffold(
        topBar = { UlyDefaultTopAppBar(title = "Settings", navigateBack = navigateBack) }
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
        ) {
            var isOpened by remember {
                mutableStateOf(false)
            }
            SettingsOpenableSection(
                isOpened = isOpened,
                onOpen = {
                    isOpened = it
                },
            ) {
                ThemeSettings(
                    themeType = uiState.theme,
                    onThemeChange = { newType ->
                        viewModel.update(SettingsEvent.UpdateThemeType(newType))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(UlyTheme.spacing.mediumSmall)
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
