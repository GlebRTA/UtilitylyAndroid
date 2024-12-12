package com.gvituskins.vituskinsandroid.presentation.bottomNavBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.gvituskins.vituskinsandroid.presentation.navigation.Routes

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

val navigationBarRoutes = listOf(
    TopLevelRoute("Profile", Routes.MainNavGraph, Icons.Default.Person),
    TopLevelRoute("Friends", Routes.MoreNavGraph, Icons.Default.Call)
)
