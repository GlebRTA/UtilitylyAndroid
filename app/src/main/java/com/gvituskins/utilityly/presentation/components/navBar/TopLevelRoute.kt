package com.gvituskins.utilityly.presentation.components.navBar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.InsertChart
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.gvituskins.utilityly.R
import com.gvituskins.utilityly.presentation.screens.main.fact.FactNavGraph
import com.gvituskins.utilityly.presentation.screens.main.more.MoreNavGraph
import com.gvituskins.utilityly.presentation.screens.main.paidUtilities.PaidNavGraph
import com.gvituskins.utilityly.presentation.screens.main.unpaidUtilitiesFeature.UnpaidNavGraph

@Immutable
data class TopLevelRoute<T : Any>(
    @StringRes val name: Int,
    val graph: T,
    val visibleNavInPortrait: Set<T>,
    val visibleNavInLandscape: Set<T>,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

val navigationBarRoutes = listOf(
    TopLevelRoute(
        name = R.string.nav_utilities,
        graph = PaidNavGraph,
        visibleNavInPortrait = PaidNavGraph.showNavigationInPortrait,
        visibleNavInLandscape = PaidNavGraph.showNavigationInLandscape,
        selectedIcon = Icons.Filled.Payments,
        unselectedIcon = Icons.Outlined.Payments
    ),
    TopLevelRoute(
        name = R.string.nav_statistics,
        graph = UnpaidNavGraph,
        visibleNavInPortrait = UnpaidNavGraph.showNavigationInPortrait,
        visibleNavInLandscape = UnpaidNavGraph.showNavigationInLandscape,
        selectedIcon = Icons.Filled.InsertChart,
        unselectedIcon = Icons.Outlined.InsertChart,
    ),
    TopLevelRoute(
        name = R.string.nav_categories,
        graph = FactNavGraph,
        visibleNavInPortrait =  FactNavGraph.showNavigationInPortrait,
        visibleNavInLandscape = FactNavGraph.showNavigationInLandscape,
        selectedIcon = Icons.Filled.Category,
        unselectedIcon = Icons.Outlined.Category,
    ),
    TopLevelRoute(
        name = R.string.nav_more,
        graph = MoreNavGraph,
        visibleNavInPortrait = MoreNavGraph.showNavigationInPortrait,
        visibleNavInLandscape = MoreNavGraph.showNavigationInLandscape,
        selectedIcon = Icons.Filled.Menu,
        unselectedIcon = Icons.Outlined.Menu,
    ),
)

private val visibleRoutesInPortrait = navigationBarRoutes
    .map { it.visibleNavInPortrait }
    .flatten()

private val visibleRoutesInLandscape = navigationBarRoutes
    .map { it.visibleNavInLandscape }
    .flatten()

fun getVisibleRoutes(isLandscape: Boolean): List<Any> {
    return if (isLandscape) visibleRoutesInLandscape else visibleRoutesInPortrait
}
