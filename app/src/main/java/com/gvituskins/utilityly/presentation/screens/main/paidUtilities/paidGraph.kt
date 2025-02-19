package com.gvituskins.utilityly.presentation.screens.main.paidUtilities

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.gvituskins.utilityly.presentation.core.navigation.BaseNavGraph
import com.gvituskins.utilityly.presentation.core.navigation.routeComposable
import com.gvituskins.utilityly.presentation.screens.main.unpaidUtilitiesFeature.UnpaidNavGraph
import kotlinx.serialization.Serializable

fun NavGraphBuilder.paidGraph(navController: NavController) {
    navigation<PaidNavGraph>(startDestination = PaidNavGraph.HomePaid) {
        routeComposable<PaidNavGraph.HomePaid> {
            PaidUtilitiesScreen(
                navigateToUtilityDetails = {
                    navController.navigate(UnpaidNavGraph.DetailsUnpaid(utilityId = it))
                }
            )
        }
    }
}

@Serializable
object PaidNavGraph : BaseNavGraph {

    @Serializable
    data object HomePaid

    override val showNavigationInPortrait = setOf(HomePaid)
    override val showNavigationInLandscape = setOf(HomePaid)
}
