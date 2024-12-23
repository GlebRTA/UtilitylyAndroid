package com.gvituskins.vituskinsandroid.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.gvituskins.vituskinsandroid.data.preferences.UhSharedPreferences
import com.gvituskins.vituskinsandroid.presentation.screens.main.more.moreGraph
import com.gvituskins.vituskinsandroid.presentation.screens.main.paidUtilities.PaidNavGraph
import com.gvituskins.vituskinsandroid.presentation.screens.main.paidUtilities.paidGraph
import com.gvituskins.vituskinsandroid.presentation.screens.main.unpaidUtilitiesFeature.unpaidGraph
import com.gvituskins.vituskinsandroid.presentation.theme.LocalNavController
import com.gvituskins.vituskinsandroid.presentation.theme.UtilityHelperTheme
import com.gvituskins.vituskinsandroid.presentation.views.UhScaffold
import com.gvituskins.vituskinsandroid.presentation.views.bottomNavBar.UHBottomNavigationBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewmodel: MainViewModel by viewModels()
    @Inject lateinit var preferences: UhSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UtilityHelperTheme(
                themeType = preferences.themeType
            ) {
                val navController = LocalNavController.current

                UhScaffold (
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { UHBottomNavigationBar() },
                ) { innerPaddings ->
                    NavHost(
                        navController = navController,
                        startDestination = PaidNavGraph,
                        modifier = Modifier.padding(innerPaddings)
                    ) {
                        paidGraph(navController = navController)
                        unpaidGraph(navController = navController)
                        moreGraph(navController = navController)
                    }
                }
            }
        }
    }
}
