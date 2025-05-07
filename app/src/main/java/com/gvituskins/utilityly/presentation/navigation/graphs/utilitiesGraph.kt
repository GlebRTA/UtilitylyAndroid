package com.gvituskins.utilityly.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.gvituskins.utilityly.presentation.navigation.BaseNavGraph
import com.gvituskins.utilityly.presentation.navigation.routeComposable
import com.gvituskins.utilityly.presentation.screens.main.utilities.UtilitiesScreen
import com.gvituskins.utilityly.presentation.screens.main.utilities.manageUtility.ManageUtilityScreen
import com.gvituskins.utilityly.presentation.screens.main.utilities.utilityDetails.UtilityDetailsScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.utilitiesGraph(navController: NavController) {
    navigation<UtilitiesNavGraph>(startDestination = UtilitiesNavGraph.Utilities) {
        routeComposable<UtilitiesNavGraph.Utilities> {
            UtilitiesScreen(
                navigateToAddUtility = { dateEpochDays ->
                    navController.navigate(UtilitiesNavGraph.ManageUtility(initDateEpochDays = dateEpochDays))
                },
                navigateToUtilityDetails = { navController.navigate(UtilitiesNavGraph.UtilityDetails(it)) },
                navigateToEditUtility = { utilityId ->
                    navController.navigate(UtilitiesNavGraph.ManageUtility(utilityId = utilityId))
                }
            )
        }

        composable<UtilitiesNavGraph.UtilityDetails> {
            UtilityDetailsScreen(
                navigateBack = { navController.navigateUp() }
            )
        }

        composable<UtilitiesNavGraph.ManageUtility> {
            ManageUtilityScreen(
                navigateBack = { navController.navigateUp() },
                navigateToAddCategory = { navController.navigate(CategoriesNavGraph.ManageCategory(null)) },
                navigateToAddCompany = { navController.navigate(MoreNavGraph.ManageCompany(null)) }
            )
        }
    }
}

@Serializable
object UtilitiesNavGraph : BaseNavGraph {

    @Serializable
    data object Utilities

    @Serializable
    data class UtilityDetails(val utilityId: Int)

    @Serializable
    data class ManageUtility(
        val initDateEpochDays: Long? = null,
        val utilityId: Int? = null
    )

    override val showNavigationInPortrait = setOf(Utilities)
    override val showNavigationInLandscape = setOf(Utilities)
}
