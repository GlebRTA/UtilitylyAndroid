package com.gvituskins.utilityly.domain.models.categories

data class Category(
    val id: Int = 0,
    val name: String,
    val description: String?,
    val iconUrl: String?
)
