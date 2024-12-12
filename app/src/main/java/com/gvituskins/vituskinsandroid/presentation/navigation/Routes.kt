package com.gvituskins.vituskinsandroid.presentation.navigation

import kotlinx.serialization.Serializable

interface Routes {

    @Serializable
    object MainNavGraph {

        @Serializable
        data object Main

        @Serializable
        data object Main1
    }

    @Serializable
    data object MoreNavGraph

}
