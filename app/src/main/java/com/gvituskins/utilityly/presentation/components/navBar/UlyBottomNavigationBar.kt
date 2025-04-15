package com.gvituskins.utilityly.presentation.components.navBar

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gvituskins.utilityly.presentation.theme.LocalNavController
import com.gvituskins.utilityly.presentation.theme.UlyTheme
import kotlinx.coroutines.delay

@Composable
fun UlyBottomNavigationBar(navController: NavController = LocalNavController.current) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        contentColor = UlyTheme.colors.onBackground
    ) {
        navigationBarRoutes.forEach { bottomItem ->
            val isSelected1 = currentDestination?.hierarchy?.any { it.hasRoute(bottomItem.graph::class) } == true
            var isSelected by remember {
                mutableStateOf(isSelected1)
            }

            LaunchedEffect(isSelected1) {
                delay(70)
                isSelected = isSelected1
            }
            /*Column(
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Max)
                    .clickable {
                        navController.navigate(bottomItem.graph) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = if (isSelected) bottomItem.selectedIcon else bottomItem.unselectedIcon,
                    contentDescription = stringResource(bottomItem.name),
                    tint = UlyTheme.colors.onBackground
                )

                Text(text = stringResource(bottomItem.name))
            }
*/
            CompositionLocalProvider(
                LocalRippleConfiguration provides RippleConfiguration(
                    color = Color.Transparent,
                    rippleAlpha = RippleAlpha(0f,0f,0f,0f)
                )
            ) {
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
}
