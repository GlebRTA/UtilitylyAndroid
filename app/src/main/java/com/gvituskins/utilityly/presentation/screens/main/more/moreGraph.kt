package com.gvituskins.utilityly.presentation.screens.main.more

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.gvituskins.utilityly.presentation.core.navigation.BaseNavGraph
import com.gvituskins.utilityly.presentation.core.navigation.routeComposable
import com.gvituskins.utilityly.presentation.screens.main.more.homeMore.HomeMoreScreen
import com.gvituskins.utilityly.presentation.screens.main.more.settings.SettingsScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.moreGraph(navController: NavController) {
    navigation<MoreNavGraph>(startDestination = MoreNavGraph.HomeMore) {
        routeComposable<MoreNavGraph.HomeMore> {
            HomeMoreScreen(
                navigateToSettings = {
                    navController.navigate(MoreNavGraph.Settings)
                }
            )
        }

        composable<MoreNavGraph.Settings> {
            SettingsScreen(
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
    object Settings

    override val showNavigationInPortrait = setOf(HomeMore)
    override val showNavigationInLandscape = setOf(HomeMore)
}
