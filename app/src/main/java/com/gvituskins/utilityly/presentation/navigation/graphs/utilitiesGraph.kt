package com.gvituskins.utilityly.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.gvituskins.utilityly.presentation.navigation.BaseNavGraph
import com.gvituskins.utilityly.presentation.navigation.routeComposable
import com.gvituskins.utilityly.presentation.screens.main.utilities.UtilitiesScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.utilitiesGraph(navController: NavController) {
    navigation<UtilitiesNavGraph>(startDestination = UtilitiesNavGraph.Utilities) {
        routeComposable<UtilitiesNavGraph.Utilities> {
            UtilitiesScreen(
                navigateToAddUtility = {}
            )
        }
    }
}

@Serializable
object UtilitiesNavGraph : BaseNavGraph {

    @Serializable
    data object Utilities

    override val showNavigationInPortrait = setOf(Utilities)
    override val showNavigationInLandscape = setOf(Utilities)
}
