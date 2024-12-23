package com.gvituskins.vituskinsandroid.presentation.views.bottomNavBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.gvituskins.vituskinsandroid.presentation.screens.main.more.MoreNavGraph
import com.gvituskins.vituskinsandroid.presentation.screens.main.paidUtilities.PaidNavGraph
import com.gvituskins.vituskinsandroid.presentation.screens.main.unpaidUtilitiesFeature.UnpaidNavGraph

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
        name = "More",
        graph = MoreNavGraph,
        visibleBottomBarRoutes = setOf(MoreNavGraph.HomeMore),
        icon = Icons.Default.Menu
    ),
)

val visibleBottomBarRoutes = navigationBarRoutes
    .map { it.visibleBottomBarRoutes }
    .flatten()
