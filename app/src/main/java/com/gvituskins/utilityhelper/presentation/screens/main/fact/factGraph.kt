package com.gvituskins.utilityhelper.presentation.screens.main.fact

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.gvituskins.utilityhelper.presentation.screens.main.fact.homeFact.FactScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.factGraph(navController: NavController) {
    navigation<FactNavGraph>(
        startDestination = FactNavGraph.HomeFact
    ) {
        composable<FactNavGraph.HomeFact> {
            FactScreen()
        }
    }
}

@Serializable
object FactNavGraph {

    @Serializable
    object HomeFact
}
