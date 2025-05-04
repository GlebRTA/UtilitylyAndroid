package com.gvituskins.utilityly.domain.models.categories

import androidx.compose.ui.graphics.Color

data class Category(
    val id: Int = 0,
    val name: String,
    val description: String?,
    val color: Color,
    val parameters: List<CategoryParameter>
)
