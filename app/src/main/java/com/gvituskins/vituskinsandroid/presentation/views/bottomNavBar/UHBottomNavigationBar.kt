package com.gvituskins.vituskinsandroid.presentation.views.bottomNavBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gvituskins.vituskinsandroid.presentation.theme.LocalNavController

@Composable
fun UHBottomNavigationBar(navController: NavController = LocalNavController.current) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = visibleBottomBarRoutes.any { navBackStackEntry?.destination?.hasRoute(it::class) ?: false }

    AnimatedVisibility(
        visible = showBottomBar,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        NavigationBar {
            navigationBarRoutes.forEach { bottomItem ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.hasRoute(bottomItem.graph::class) } == true,
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
                            imageVector = bottomItem.icon,
                            contentDescription = bottomItem.name
                        )
                    },
                    label = { Text(text = bottomItem.name) }
                )
            }
        }
    }
}
