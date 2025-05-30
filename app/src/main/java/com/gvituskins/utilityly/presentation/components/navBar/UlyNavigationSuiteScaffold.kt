package com.gvituskins.utilityly.presentation.components.navBar

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.only
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuite
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gvituskins.utilityly.presentation.theme.LocalNavController

@Composable
private fun Modifier.animateNavigationItem(interactionSource: MutableInteractionSource): Modifier {
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.8f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessMediumLow)
    )

    return this then Modifier.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}

@Composable
fun UlyNavigationSuiteScaffold(
    modifier: Modifier = Modifier,
    navigationSuiteColors: NavigationSuiteColors = NavigationSuiteDefaults.colors(),
    containerColor: Color = NavigationSuiteScaffoldDefaults.containerColor,
    contentColor: Color = NavigationSuiteScaffoldDefaults.contentColor,
    content: @Composable () -> Unit = {},
) {
    val navController = LocalNavController.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val showNavigationBar = getVisibleRoutes(isLandscape).any { navBackStackEntry?.destination?.hasRoute(it::class) ?: false }

    val layoutType = if (!showNavigationBar) {
        NavigationSuiteType.None
    } else if (isLandscape) {
        NavigationSuiteType.NavigationRail
    } else {
        NavigationSuiteType.NavigationBar
    }

    Surface(
        modifier = modifier,
        color = containerColor,
        contentColor = contentColor
    ) {
        NavigationSuiteScaffoldLayout(
            navigationSuite = {
                AnimatedVisibility(
                    visible = showNavigationBar,
                    enter = if (isLandscape) {
                        slideInHorizontally(
                            animationSpec = tween(300),
                            initialOffsetX = { -it }
                        )
                    } else {
                        slideInVertically(
                            animationSpec = tween(300),
                            initialOffsetY = { it }
                        )
                    },
                ) {
                    NavigationSuite(
                        layoutType = layoutType,
                        colors = navigationSuiteColors,
                        content = {
                            navigationBarRoutes.forEach { bottomItem ->
                                val isSelected = currentDestination?.hierarchy?.any { it.hasRoute(bottomItem.graph::class) } == true
                                item(
                                    selected = isSelected,
                                    onClick = {
                                        navController.navigate(bottomItem.graph) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (isSelected) bottomItem.selectedIcon else bottomItem.unselectedIcon,
                                            contentDescription = stringResource(bottomItem.name),
                                            modifier = Modifier.animateNavigationItem(bottomItem.interactionSource)
                                        )
                                    },
                                    label = { Text(text =  stringResource(bottomItem.name)) },
                                    interactionSource = bottomItem.interactionSource
                                )
                            }
                        },
                    )
                }
            },
            layoutType = layoutType,
            content = {
                Box(
                    modifier = Modifier.consumeWindowInsets(
                        when (layoutType) {
                            NavigationSuiteType.NavigationBar -> NavigationBarDefaults.windowInsets.only(
                                WindowInsetsSides.Bottom
                            )

                            NavigationSuiteType.NavigationRail -> NavigationRailDefaults.windowInsets.only(
                                WindowInsetsSides.Start
                            )

                            NavigationSuiteType.NavigationDrawer -> DrawerDefaults.windowInsets.only(
                                WindowInsetsSides.Start
                            )

                            else -> WindowInsets(0, 0, 0, 0)
                        }
                    )
                ) {
                    content()
                }
            }
        )
    }
}
