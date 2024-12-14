package com.gvituskins.vituskinsandroid.presentation.bottomNavBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.gvituskins.vituskinsandroid.presentation.navigation.MainNavGraph
import com.gvituskins.vituskinsandroid.presentation.navigation.MoreNavGraph

data class TopLevelRoute<T : Any>(
    val name: String,
    val graph: T,
    val visibleBottomBarRoutes: List<T>,
    val icon: ImageVector,
)

val navigationBarRoutes = listOf(
    TopLevelRoute(
        name = "Profile",
        graph = MainNavGraph,
        visibleBottomBarRoutes = listOf(MainNavGraph.Main, MainNavGraph.Main1),
        icon = Icons.Default.Person
    ),
    TopLevelRoute(
        name = "Friends",
        graph = MoreNavGraph,
        visibleBottomBarRoutes = listOf(MoreNavGraph.More),
        icon = Icons.Default.Call
    )
)
