package com.gvituskins.utilityly.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.navigation.compose.NavHost
import com.gvituskins.utilityly.data.preferences.UlySharedPreferences
import com.gvituskins.utilityly.presentation.components.navBar.UlyNavigationSuiteScaffold
import com.gvituskins.utilityly.presentation.screens.main.fact.factGraph
import com.gvituskins.utilityly.presentation.screens.main.more.moreGraph
import com.gvituskins.utilityly.presentation.screens.main.paidUtilities.PaidNavGraph
import com.gvituskins.utilityly.presentation.screens.main.paidUtilities.paidGraph
import com.gvituskins.utilityly.presentation.screens.main.unpaidUtilitiesFeature.unpaidGraph
import com.gvituskins.utilityly.presentation.theme.LocalNavController
import com.gvituskins.utilityly.presentation.theme.UtilitylyTheme
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
            UtilitylyTheme(themeType = preferences.themeType) {
                val navController = LocalNavController.current

                UlyNavigationSuiteScaffold(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = PaidNavGraph,
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None},
                        popExitTransition = {
                            scaleOut(
                                targetScale = 0.9f,
                                transformOrigin = TransformOrigin(pivotFractionX = 0.5f, pivotFractionY = 0.5f)
                            )
                        },
                        popEnterTransition = { EnterTransition.None },
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
