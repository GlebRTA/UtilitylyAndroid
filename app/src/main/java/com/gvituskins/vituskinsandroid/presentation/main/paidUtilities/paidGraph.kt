package com.gvituskins.vituskinsandroid.presentation.main.paidUtilities

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.gvituskins.vituskinsandroid.presentation.main.unpaidUtilitiesFeature.UnpaidNavGraph
import kotlinx.serialization.Serializable

fun NavGraphBuilder.paidGraph(navController: NavController) {
    navigation<PaidNavGraph>(startDestination = PaidNavGraph.HomePaid) {
        composable<PaidNavGraph.HomePaid> {
            PaidUtilitiesScreen(
                navigateToUtilityDetails = {
                    navController.navigate(UnpaidNavGraph.DetailsUnpaid(utilityId = it))
                }
            )
        }
    }

}

@Serializable
object PaidNavGraph {

    @Serializable
    data object HomePaid
}
