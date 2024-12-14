package com.gvituskins.vituskinsandroid.presentation.navigation

import kotlinx.serialization.Serializable


@Serializable
object MainNavGraph {

    @Serializable
    data object Main

    @Serializable
    data object Main1

    @Serializable
    data object Main2
}

@Serializable
object MoreNavGraph {

    @Serializable
    data object More
}
