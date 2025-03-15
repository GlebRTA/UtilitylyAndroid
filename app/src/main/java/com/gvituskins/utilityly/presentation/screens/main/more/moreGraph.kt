package com.gvituskins.utilityly.presentation.screens.main.more

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.gvituskins.utilityly.presentation.core.navigation.BaseNavGraph
import com.gvituskins.utilityly.presentation.core.navigation.routeComposable
import com.gvituskins.utilityly.presentation.screens.main.more.locations.LocationsScreen
import com.gvituskins.utilityly.presentation.screens.main.more.more.MoreScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.moreGraph(navController: NavController) {
    navigation<MoreNavGraph>(startDestination = MoreNavGraph.HomeMore) {
        routeComposable<MoreNavGraph.HomeMore> {
            MoreScreen(
                navigateToLocations = { navController.navigate(MoreNavGraph.Locations) },
                navigateToCompanies = {}
            )
        }

        composable<MoreNavGraph.Locations> {
            LocationsScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}

@Serializable
object MoreNavGraph : BaseNavGraph {

    @Serializable
    object HomeMore

    @Serializable
    object Locations

    override val showNavigationInPortrait = setOf(HomeMore)
    override val showNavigationInLandscape = setOf(HomeMore)
}
