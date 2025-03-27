package com.gvituskins.utilityly.presentation.components.navBar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gvituskins.utilityly.presentation.theme.LocalNavController

@Composable
fun UlyBottomNavigationBar(navController: NavController = LocalNavController.current) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        navigationBarRoutes.forEach { bottomItem ->
            val isSelected = currentDestination?.hierarchy?.any { it.hasRoute(bottomItem.graph::class) } == true
            NavigationBarItem(
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
                    )
                },
                label = { Text(text = stringResource(bottomItem.name)) },
            )
        }
    }
}
