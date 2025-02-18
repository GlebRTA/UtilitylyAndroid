package com.gvituskins.utilityly.presentation.views.bottomNavBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.gvituskins.utilityly.presentation.screens.main.fact.FactNavGraph
import com.gvituskins.utilityly.presentation.screens.main.more.MoreNavGraph
import com.gvituskins.utilityly.presentation.screens.main.paidUtilities.PaidNavGraph
import com.gvituskins.utilityly.presentation.screens.main.unpaidUtilitiesFeature.UnpaidNavGraph

data class TopLevelRoute<T : Any>(
    val name: String,
    val graph: T,
    val visibleBottomBarRoutes: Set<T>,
    val icon: ImageVector,
)

val navigationBarRoutes = listOf(
    TopLevelRoute(
        name = "Home",
        graph = PaidNavGraph,
        visibleBottomBarRoutes = setOf(PaidNavGraph.HomePaid),
        icon = Icons.Default.Home
    ),
    TopLevelRoute(
        name = "Statistics",
        graph = UnpaidNavGraph,
        visibleBottomBarRoutes = setOf(UnpaidNavGraph.HomeUnpaid),
        icon = Icons.Default.BarChart
    ),
    TopLevelRoute(
        name = "Categories",
        graph = FactNavGraph,
        visibleBottomBarRoutes = setOf(FactNavGraph.HomeFact),
        icon = Icons.Default.Category
    ),
    TopLevelRoute(
        name = "More",
        graph = MoreNavGraph,
        visibleBottomBarRoutes = setOf(MoreNavGraph.HomeMore),
        icon = Icons.Default.Menu
    ),
)

val visibleBottomBarRoutes = navigationBarRoutes
    .map { it.visibleBottomBarRoutes }
    .flatten()
