package com.gvituskins.utilityly.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import com.gvituskins.utilityly.domain.models.enums.ThemeType
import com.gvituskins.utilityly.presentation.components.navBar.UlyNavigationSuiteScaffold
import com.gvituskins.utilityly.presentation.navigation.graphs.UtilitiesNavGraph
import com.gvituskins.utilityly.presentation.navigation.graphs.categoriesGraph
import com.gvituskins.utilityly.presentation.navigation.graphs.moreGraph
import com.gvituskins.utilityly.presentation.navigation.graphs.statisticsGraph
import com.gvituskins.utilityly.presentation.navigation.graphs.utilitiesGraph
import com.gvituskins.utilityly.presentation.theme.LocalNavController
import com.gvituskins.utilityly.presentation.theme.UtilitylyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewmodel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleEdgeToEdgeColors()

        setContent {
            val currentTheme by viewmodel.uiState.collectAsStateWithLifecycle()

            UtilitylyTheme(themeType = currentTheme.themeType) {
                val navController = LocalNavController.current

                UlyNavigationSuiteScaffold(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = UtilitiesNavGraph,
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                        popExitTransition = {
                            scaleOut(
                                targetScale = 0.9f,
                                transformOrigin = TransformOrigin(
                                    pivotFractionX = 0.5f,
                                    pivotFractionY = 0.5f
                                )
                            )
                        },
                        popEnterTransition = { EnterTransition.None },
                    ) {
                        utilitiesGraph(navController = navController)
                        statisticsGraph(navController = navController)
                        categoriesGraph(navController = navController)
                        moreGraph(navController = navController)
                    }
                }
            }
        }
    }

    private fun handleEdgeToEdgeColors() {
        viewmodel.uiState
            .onEach { state ->
                when (state.themeType) {
                    ThemeType.SYSTEM -> enableEdgeToEdge()
                    ThemeType.LIGHT -> enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
                        navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
                    )

                    ThemeType.DARK -> enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
                        navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
                    )
                }
            }
            .launchIn(lifecycleScope)
    }
}
