package com.gvituskins.vituskinsandroid.presentation.screens.main.more

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.gvituskins.vituskinsandroid.presentation.screens.main.more.homeMore.HomeMoreScreen
import com.gvituskins.vituskinsandroid.presentation.screens.main.more.settings.SettingsScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.moreGraph(navController: NavController) {
    navigation<MoreNavGraph>(startDestination = MoreNavGraph.HomeMore) {
        composable<MoreNavGraph.HomeMore> {
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
object MoreNavGraph {

    @Serializable
    object HomeMore

    @Serializable
    object Settings
}
