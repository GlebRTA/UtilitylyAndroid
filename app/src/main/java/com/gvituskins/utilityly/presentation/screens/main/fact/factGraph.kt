package com.gvituskins.utilityly.presentation.screens.main.fact

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.gvituskins.utilityly.presentation.core.navigation.BaseNavGraph
import com.gvituskins.utilityly.presentation.core.navigation.routeComposable
import com.gvituskins.utilityly.presentation.screens.main.fact.homeFact.FactScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.factGraph(navController: NavController) {
    navigation<FactNavGraph>(
        startDestination = FactNavGraph.HomeFact
    ) {
        routeComposable<FactNavGraph.HomeFact> {
            FactScreen()
        }
    }
}

@Serializable
object FactNavGraph : BaseNavGraph {

    @Serializable
    object HomeFact

    override val showNavigationInPortrait = setOf(HomeFact)
    override val showNavigationInLandscape = setOf(HomeFact)
}
