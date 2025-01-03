package com.gvituskins.utilityhelper.presentation.screens.main.unpaidUtilitiesFeature

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.gvituskins.utilityhelper.presentation.screens.main.unpaidUtilitiesFeature.detailsUnpaidUtilities.DetailsUnpaidScreen
import com.gvituskins.utilityhelper.presentation.screens.main.unpaidUtilitiesFeature.homeUnpaidUtilities.HomeUnpaidUtilitiesScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.unpaidGraph(navController: NavController) {
    navigation<UnpaidNavGraph>(startDestination = UnpaidNavGraph.HomeUnpaid) {
        composable<UnpaidNavGraph.HomeUnpaid> {
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
object UnpaidNavGraph {

    @Serializable
    data object HomeUnpaid

    @Serializable
    data class DetailsUnpaid(val utilityId: Int)
}
