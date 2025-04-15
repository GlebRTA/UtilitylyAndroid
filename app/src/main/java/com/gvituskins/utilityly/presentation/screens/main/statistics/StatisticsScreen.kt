package com.gvituskins.utilityly.presentation.screens.main.statistics

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gvituskins.utilityly.R
import com.gvituskins.utilityly.presentation.components.containers.UlyScaffold
import com.gvituskins.utilityly.presentation.components.navBar.UlyBottomNavigationBar
import com.gvituskins.utilityly.presentation.components.topAppBars.UlyDefaultTopAppBar

@Composable
fun StatisticsScreen() {

    UlyScaffold(
        topBar = { UlyDefaultTopAppBar(title = stringResource(R.string.nav_statistics)) },
        bottomBar = { UlyBottomNavigationBar() },
    ) { innerPaddings ->

    }

}
