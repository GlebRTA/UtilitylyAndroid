package com.gvituskins.utilityly.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.gvituskins.utilityly.presentation.navigation.BaseNavGraph
import com.gvituskins.utilityly.presentation.navigation.routeComposable
import com.gvituskins.utilityly.presentation.screens.main.companies.CompaniesScreen
import com.gvituskins.utilityly.presentation.screens.main.companies.manageCompany.ManageCompanyScreen
import com.gvituskins.utilityly.presentation.screens.main.locations.LocationsScreen
import com.gvituskins.utilityly.presentation.screens.main.more.MoreScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.moreGraph(navController: NavController) {
    navigation<MoreNavGraph>(startDestination = MoreNavGraph.HomeMore) {
        routeComposable<MoreNavGraph.HomeMore> {
            MoreScreen(
                navigateToLocations = { navController.navigate(MoreNavGraph.Locations) },
                navigateToCompanies = { navController.navigate(MoreNavGraph.Companies) }
            )
        }

        composable<MoreNavGraph.Locations> {
            LocationsScreen(
                navigateBack = { navController.navigateUp() }
            )
        }

        composable<MoreNavGraph.Companies> {
            CompaniesScreen(
                navigateBack = { navController.navigateUp() },
                navigateToAddCompany = { navController.navigate(MoreNavGraph.ManageCompany(null)) },
                navigateToEditCompany = { companyId ->
                    navController.navigate(MoreNavGraph.ManageCompany(companyId))
                }
            )
        }

        composable<MoreNavGraph.ManageCompany> {
            ManageCompanyScreen(
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
    object Locations

    @Serializable
    object Companies

    @Serializable
    data class ManageCompany(val companyId: Int?)

    override val showNavigationInPortrait = setOf(HomeMore)
    override val showNavigationInLandscape = setOf(HomeMore)
}
