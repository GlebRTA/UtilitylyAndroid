package com.gvituskins.utilityhelper.presentation.views.bottomNavBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import com.gvituskins.utilityhelper.presentation.screens.main.fact.FactNavGraph
import com.gvituskins.utilityhelper.presentation.screens.main.more.MoreNavGraph
import com.gvituskins.utilityhelper.presentation.screens.main.paidUtilities.PaidNavGraph
import com.gvituskins.utilityhelper.presentation.screens.main.unpaidUtilitiesFeature.UnpaidNavGraph

data class TopLevelRoute<T : Any>(
    val name: String,
    val graph: T,
    val visibleBottomBarRoutes: Set<T>,
    val icon: ImageVector,
)

val navigationBarRoutes = listOf(
    TopLevelRoute(
        name = "Paid",
        graph = PaidNavGraph,
        visibleBottomBarRoutes = setOf(PaidNavGraph.HomePaid),
        icon = Icons.Default.Check
    ),
    TopLevelRoute(
        name = "Unpaid",
        graph = UnpaidNavGraph,
        visibleBottomBarRoutes = setOf(UnpaidNavGraph.HomeUnpaid),
        icon = Icons.Default.Close
    ),
    TopLevelRoute(
        name = "Random Fact",
        graph = FactNavGraph,
        visibleBottomBarRoutes = setOf(FactNavGraph.HomeFact),
        icon = Icons.Default.Warning
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
