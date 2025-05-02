package com.gvituskins.utilityly.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.gvituskins.utilityly.presentation.navigation.BaseNavGraph
import com.gvituskins.utilityly.presentation.navigation.routeComposable
import com.gvituskins.utilityly.presentation.screens.main.statistics.StatisticsScreen
import com.gvituskins.utilityly.presentation.screens.main.statistics.statTable.table.TableScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.statisticsGraph(navController: NavController) {
    navigation<StatisticsNavGraph>(startDestination = StatisticsNavGraph.Statistics) {
        routeComposable<StatisticsNavGraph.Statistics> {
            StatisticsScreen(
                navigateToTable = { year ->
                    navController.navigate(StatisticsNavGraph.Table(year))
                }
            )
        }

        composable<StatisticsNavGraph.Table> {
            TableScreen(navigateBack = { navController.navigateUp() })
        }
    }
}

@Serializable
object StatisticsNavGraph : BaseNavGraph {

    @Serializable
    data object Statistics

    @Serializable
    data class Table(val year: Int)

    override val showNavigationInPortrait = setOf(Statistics)
    override val showNavigationInLandscape = setOf(Statistics)
}
