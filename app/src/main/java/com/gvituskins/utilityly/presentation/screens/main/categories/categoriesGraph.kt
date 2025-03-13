package com.gvituskins.utilityly.presentation.screens.main.categories

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.gvituskins.utilityly.presentation.core.navigation.BaseNavGraph
import com.gvituskins.utilityly.presentation.core.navigation.routeComposable
import com.gvituskins.utilityly.presentation.screens.main.categories.categories.CategoriesScreen
import com.gvituskins.utilityly.presentation.screens.main.categories.manageCategory.ManageCategoryScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.categoriesGraph(navController: NavController) {
    navigation<CategoriesNavGraph>(
        startDestination = CategoriesNavGraph.Categories
    ) {
        routeComposable<CategoriesNavGraph.Categories> {
            CategoriesScreen(
                navigateToAddCategory = {
                    navController.navigate(CategoriesNavGraph.ManageCategory(null))
                },
                navigateToEditCategory = { categoryId ->
                    navController.navigate(CategoriesNavGraph.ManageCategory(categoryId))
                }
            )
        }

        composable<CategoriesNavGraph.ManageCategory> {
            ManageCategoryScreen(
                navigateBack = { navController.navigateUp() }
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
