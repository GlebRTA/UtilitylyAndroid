package com.gvituskins.utilityly.presentation

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import com.gvituskins.utilityly.domain.models.enums.ThemeType
import com.gvituskins.utilityly.presentation.components.navBar.UlyNavigationSuiteScaffold
import com.gvituskins.utilityly.presentation.components.snackbar.SnackbarController
import com.gvituskins.utilityly.presentation.core.utils.collectAsOneTimeEvent
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

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope> {
    error("SharedTransitionScope is not provided")
}

val LocalAnimatedContentScopeScope = compositionLocalOf<AnimatedContentScope> {
    error("AnimatedContentScope is not provided")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewmodel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleEdgeToEdgeColors()

        setContent {
            val currentTheme by viewmodel.uiState.collectAsStateWithLifecycle()

            UtilitylyTheme(themeType = currentTheme.themeType) {
                val navController = LocalNavController.current

                val snackbarHostState = remember {
                    SnackbarHostState()
                }

                SnackbarController.events.collectAsOneTimeEvent(key1 = snackbarHostState) { event ->
                    snackbarHostState.currentSnackbarData?.dismiss()

                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action?.name,
                        duration = event.duration
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        event.action?.action?.invoke()
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                ) {
                    UlyNavigationSuiteScaffold(modifier = Modifier.fillMaxSize()) {
                        SharedTransitionLayout {
                            CompositionLocalProvider(LocalSharedTransitionScope provides this) {
                                NavHost(
                                    navController = navController,
                                    startDestination = UtilitiesNavGraph,
                                    enterTransition = {
                                        slideInVertically(
                                            initialOffsetY = { it },
                                            animationSpec = tween(400)
                                        )
                                    },
                                    exitTransition = { ExitTransition.None },
                                    popEnterTransition = { EnterTransition.None },
                                    popExitTransition = {
                                        slideOutVertically(
                                            targetOffsetY = { it },
                                            animationSpec = tween(400)
                                        )
                                    },
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
