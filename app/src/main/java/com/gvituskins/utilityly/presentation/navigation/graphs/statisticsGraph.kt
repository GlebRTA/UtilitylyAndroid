package com.gvituskins.utilityly.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.gvituskins.utilityly.presentation.navigation.BaseNavGraph
import com.gvituskins.utilityly.presentation.navigation.routeComposable
import com.gvituskins.utilityly.presentation.screens.main.statistics.StatisticsScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.statisticsGraph(navController: NavController) {
    navigation<StatisticsNavGraph>(startDestination = StatisticsNavGraph.Statistics) {
        routeComposable<StatisticsNavGraph.Statistics> {
            StatisticsScreen()
        }
    }
}

@Serializable
object StatisticsNavGraph : BaseNavGraph {

    @Serializable
    data object Statistics

    override val showNavigationInPortrait = setOf(Statistics)
    override val showNavigationInLandscape = setOf(Statistics)
}
