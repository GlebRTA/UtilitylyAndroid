package com.gvituskins.utilityly.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.gvituskins.utilityly.data.preferences.UlySharedPreferences
import com.gvituskins.utilityly.presentation.screens.main.fact.factGraph
import com.gvituskins.utilityly.presentation.screens.main.more.moreGraph
import com.gvituskins.utilityly.presentation.screens.main.paidUtilities.PaidNavGraph
import com.gvituskins.utilityly.presentation.screens.main.paidUtilities.paidGraph
import com.gvituskins.utilityly.presentation.screens.main.unpaidUtilitiesFeature.unpaidGraph
import com.gvituskins.utilityly.presentation.theme.LocalNavController
import com.gvituskins.utilityly.presentation.theme.UtilitylyTheme
import com.gvituskins.utilityly.presentation.views.UlyScaffold
import com.gvituskins.utilityly.presentation.views.bottomNavBar.UlyBottomNavigationBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewmodel: MainViewModel by viewModels()
    @Inject lateinit var preferences: UlySharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UtilitylyTheme(
                themeType = preferences.themeType
            ) {
                val navController = LocalNavController.current

                UlyScaffold (
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { UlyBottomNavigationBar() },
                ) { innerPaddings ->
                    NavHost(
                        navController = navController,
                        startDestination = PaidNavGraph,
                        modifier = Modifier.padding(innerPaddings)
                    ) {
                        paidGraph(navController = navController)
                        unpaidGraph(navController = navController)
                        factGraph(navController = navController)
                        moreGraph(navController = navController)
                    }
                }
            }
        }
    }
}
