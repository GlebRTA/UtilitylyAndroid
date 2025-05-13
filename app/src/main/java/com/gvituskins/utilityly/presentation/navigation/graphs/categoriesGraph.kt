package com.gvituskins.utilityly.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.gvituskins.utilityly.presentation.navigation.BaseNavGraph
import com.gvituskins.utilityly.presentation.navigation.routeComposable
import com.gvituskins.utilityly.presentation.navigation.ulyComposable
import com.gvituskins.utilityly.presentation.screens.main.categories.CategoriesScreen
import com.gvituskins.utilityly.presentation.screens.main.categories.manageCategory.ManageCategoryScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.categoriesGraph(navController: NavController) {
    navigation<CategoriesNavGraph>(
        startDestination = CategoriesNavGraph.Categories
    ) {
        routeComposable<CategoriesNavGraph.Categories> {
            CategoriesScreen(
                navigateToAddCategory = {
                    navController.navigate(CategoriesNavGraph.ManageCategory(null)) {
                        launchSingleTop = true
                    }
                },
                navigateToEditCategory = { categoryId ->
                    navController.navigate(CategoriesNavGraph.ManageCategory(categoryId)) {
                        launchSingleTop = true
                    }
                },
            )
        }

        ulyComposable<CategoriesNavGraph.ManageCategory> {
            ManageCategoryScreen(
                navigateBack = { navController.navigateUp() },
            )
        }
    }
}

@Serializable
object CategoriesNavGraph : BaseNavGraph {

    @Serializable
    object Categories

    @Serializable
    data class ManageCategory(val categoryId: Int?)

    override val showNavigationInPortrait = setOf(Categories)
    override val showNavigationInLandscape = setOf(Categories, ManageCategory(-1))
}
