package com.gvituskins.utilityly.data.mappers

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt
import com.gvituskins.utilityly.data.db.entities.CategoryEntity
import com.gvituskins.utilityly.data.db.entities.ParameterCategoryEntity
import com.gvituskins.utilityly.domain.models.categories.Category
import com.gvituskins.utilityly.domain.models.categories.CategoryParameter

fun CategoryEntity.toCategory(parameters: List<ParameterCategoryEntity>): Category {
    return Category(
        id = id,
        name = name,
        description = description,
        color = Color("#${colorHex}".toColorInt()),
        parameters = parameters.map { it.toCategoryParameter() }
    )
}

@OptIn(ExperimentalStdlibApi::class)
fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        description = description,
        colorHex = color.toArgb().toHexString()
    )
}

fun ParameterCategoryEntity.toCategoryParameter(): CategoryParameter {
    return CategoryParameter(
        id = id,
        name = name
    )
}

fun CategoryParameter.toCategoryParameterEntity(categoryId: Int = 0): ParameterCategoryEntity {
    return ParameterCategoryEntity(
        id = id,
        categoryId = categoryId,
        name = name,
    )
}
