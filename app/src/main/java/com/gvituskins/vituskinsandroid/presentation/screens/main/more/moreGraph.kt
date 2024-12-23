package com.gvituskins.vituskinsandroid.presentation.screens.main.more

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.gvituskins.vituskinsandroid.presentation.screens.main.more.homeMore.HomeMoreScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.moreGraph(navController: NavController) {
    navigation<MoreNavGraph>(startDestination = MoreNavGraph.HomeMore) {
        composable<MoreNavGraph.HomeMore> {
            HomeMoreScreen()
        }
    }
}

@Serializable
object MoreNavGraph {

    @Serializable
    object HomeMore
}
