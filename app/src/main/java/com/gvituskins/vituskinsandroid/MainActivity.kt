package com.gvituskins.vituskinsandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.gvituskins.vituskinsandroid.presentation.bottomNavBar.navigationBarRoutes
import com.gvituskins.vituskinsandroid.presentation.navigation.Routes
import com.gvituskins.vituskinsandroid.presentation.theme.VituskinsAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VituskinsAndroidTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        NavigationBar {
                            navigationBarRoutes.forEach { bottomItem ->
                                NavigationBarItem(
                                    selected = currentDestination?.hierarchy?.any { it.hasRoute(bottomItem.route::class) } == true,
                                    onClick = {
                                        navController.navigate(bottomItem.route) {
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
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.MainNavGraph,
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        navigation<Routes.MainNavGraph>(startDestination = Routes.MainNavGraph.Main) {
                            composable<Routes.MainNavGraph.Main> {
                                Text(text = "Main")
                                Button(
                                    onClick = { navController.navigate(Routes.MainNavGraph.Main1) }
                                ) {
                                    Text(text = "Main1")
                                }
                            }

                            composable<Routes.MainNavGraph.Main1> {
                                Text(text = "Main1")
                            }
                        }


                        composable<Routes.MoreNavGraph> {
                            Text(text = "More")
                        }
                    }
                }
            }
        }
    }
}
