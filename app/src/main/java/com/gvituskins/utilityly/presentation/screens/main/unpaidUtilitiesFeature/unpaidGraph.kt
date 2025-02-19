package com.gvituskins.utilityly.presentation.screens.main.unpaidUtilitiesFeature

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.gvituskins.utilityly.presentation.core.navigation.BaseNavGraph
import com.gvituskins.utilityly.presentation.core.navigation.routeComposable
import com.gvituskins.utilityly.presentation.screens.main.unpaidUtilitiesFeature.detailsUnpaidUtilities.DetailsUnpaidScreen
import com.gvituskins.utilityly.presentation.screens.main.unpaidUtilitiesFeature.homeUnpaidUtilities.HomeUnpaidUtilitiesScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.unpaidGraph(navController: NavController) {
    navigation<UnpaidNavGraph>(startDestination = UnpaidNavGraph.HomeUnpaid) {
        routeComposable<UnpaidNavGraph.HomeUnpaid> {
            HomeUnpaidUtilitiesScreen(
                navigateToUtilityDetails = { utilityId ->
                    navController.navigate(UnpaidNavGraph.DetailsUnpaid(utilityId = utilityId))
                }
            )
        }

        composable<UnpaidNavGraph.DetailsUnpaid> {
            DetailsUnpaidScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}

@Serializable
object UnpaidNavGraph : BaseNavGraph {

    @Serializable
    data object HomeUnpaid

    @Serializable
    data class DetailsUnpaid(val utilityId: Int)

    override val showNavigationInPortrait = setOf(HomeUnpaid)
    override val showNavigationInLandscape = setOf(HomeUnpaid, DetailsUnpaid(-1))
}
