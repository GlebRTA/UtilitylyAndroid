package com.gvituskins.utilityly.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.gvituskins.utilityly.presentation.navigation.BaseNavGraph
import com.gvituskins.utilityly.presentation.navigation.routeComposable
import com.gvituskins.utilityly.presentation.screens.main.utilities.UtilitiesScreen
import com.gvituskins.utilityly.presentation.screens.main.utilities.utilityDetails.UtilityDetailsScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.utilitiesGraph(navController: NavController) {
    navigation<UtilitiesNavGraph>(startDestination = UtilitiesNavGraph.Utilities) {
        routeComposable<UtilitiesNavGraph.Utilities> {
            UtilitiesScreen(
                navigateToAddUtility = {},
                navigateToUtilityDetails = { navController.navigate(UtilitiesNavGraph.UtilityDetails) }
            )
        }

        composable<UtilitiesNavGraph.UtilityDetails> {
            UtilityDetailsScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}

@Serializable
object UtilitiesNavGraph : BaseNavGraph {

    @Serializable
    data object Utilities

    @Serializable
    data object UtilityDetails

    override val showNavigationInPortrait = setOf(Utilities)
    override val showNavigationInLandscape = setOf(Utilities)
}
