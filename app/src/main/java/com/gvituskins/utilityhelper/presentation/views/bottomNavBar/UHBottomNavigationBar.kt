package com.gvituskins.utilityhelper.presentation.views.bottomNavBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gvituskins.utilityhelper.presentation.theme.LocalNavController
import kotlinx.coroutines.launch

@Composable
fun UHBottomNavigationBar(navController: NavController = LocalNavController.current) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = visibleBottomBarRoutes.any { navBackStackEntry?.destination?.hasRoute(it::class) ?: false }
    val scope = rememberCoroutineScope()
    val startAnimationScale = 1f
    val topAnimationScale = 1.4f

    AnimatedVisibility(
        visible = showBottomBar,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        NavigationBar {
            navigationBarRoutes.forEach { bottomItem ->
                val animateIcon = remember { Animatable(initialValue = startAnimationScale) }
                val isSelected = currentDestination?.hierarchy?.any { it.hasRoute(bottomItem.graph::class) } == true
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        scope.launch {
                            navController.navigate(bottomItem.graph) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            if (!isSelected) {
                                animateIcon.animateTo(
                                    targetValue = topAnimationScale,
                                    animationSpec = tween(durationMillis = 300)
                                )
                                animateIcon.animateTo(
                                    targetValue = startAnimationScale,
                                    animationSpec = tween(durationMillis = 300)
                                )
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = bottomItem.icon,
                            contentDescription = bottomItem.name,
                            modifier = Modifier.graphicsLayer {
                                scaleX = animateIcon.value
                                scaleY = animateIcon.value
                            }
                        )
                    },
                    label = { Text(text = bottomItem.name) },
                )
            }
        }
    }
}
