package com.gvituskins.utilityhelper.presentation.screens.main.more.settings

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
import com.gvituskins.utilityhelper.domain.models.enums.ThemeType
import com.gvituskins.utilityhelper.presentation.theme.LocalThemeType
import com.gvituskins.utilityhelper.presentation.theme.UhTheme
import com.gvituskins.utilityhelper.presentation.views.UhScaffold
import com.gvituskins.utilityhelper.presentation.views.sections.SettingsOpenableSection
import com.gvituskins.utilityhelper.presentation.views.topAppBars.UhDefaultTopAppBar

@Composable
fun SettingsScreen(
    navigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val globalThemeState = LocalThemeType.current

    UhScaffold(
        topBar = { UhDefaultTopAppBar(title = "Settings", navigateBack = navigateBack) }
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
                        when (newType) {
                            ThemeType.SYSTEM -> {
                                viewModel.update(SettingsEvent.UpdateThemeType(ThemeType.SYSTEM))
                                globalThemeState.value = ThemeType.SYSTEM
                            }
                            ThemeType.LIGHT -> {
                                viewModel.update(SettingsEvent.UpdateThemeType(ThemeType.LIGHT))
                                globalThemeState.value = ThemeType.LIGHT
                            }
                            ThemeType.DARK -> {
                                viewModel.update(SettingsEvent.UpdateThemeType(ThemeType.DARK))
                                globalThemeState.value = ThemeType.DARK
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(UhTheme.spacing.mediumSmall)
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
            shape = UhTheme.shapes.medium.copy(
                topEnd = CornerSize(0.dp),
                bottomEnd = CornerSize(0.dp)
            )
        ) {
            Text(text = "System")
        }

        SegmentedButton(
            selected = themeType == ThemeType.LIGHT,
            onClick = { onThemeChange(ThemeType.LIGHT) },
            shape = UhTheme.shapes.none
        ) {
            Text(text = "Light")
        }

        SegmentedButton(
            selected = themeType == ThemeType.DARK,
            onClick = { onThemeChange(ThemeType.DARK) },
            shape = UhTheme.shapes.medium.copy(
                topStart = CornerSize(0.dp),
                bottomStart = CornerSize(0.dp)
            )
        ) {
            Text(text = "Dark")
        }
    }
}
