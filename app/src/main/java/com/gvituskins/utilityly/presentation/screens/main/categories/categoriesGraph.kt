package com.gvituskins.utilityly.presentation.screens.main.categories

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.gvituskins.utilityly.presentation.core.navigation.BaseNavGraph
import com.gvituskins.utilityly.presentation.core.navigation.routeComposable
import com.gvituskins.utilityly.presentation.screens.main.categories.categories.CategoriesScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.categoriesGraph(navController: NavController) {
    navigation<CategoriesNavGraph>(
        startDestination = CategoriesNavGraph.Categories
    ) {
        routeComposable<CategoriesNavGraph.Categories> {
            CategoriesScreen()
        }
    }
}

@Serializable
object CategoriesNavGraph : BaseNavGraph {

    @Serializable
    object Categories

    override val showNavigationInPortrait = setOf(Categories)
    override val showNavigationInLandscape = setOf(Categories)
}
