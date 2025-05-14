package com.gvituskins.utilityly.data.mappers

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.gvituskins.utilityly.data.db.entities.CategoryEntity
import com.gvituskins.utilityly.data.db.entities.ParameterCategoryEntity
import com.gvituskins.utilityly.data.db.entities.ParameterValueEntity
import com.gvituskins.utilityly.domain.models.categories.Category
import com.gvituskins.utilityly.domain.models.categories.CategoryParameter
import com.gvituskins.utilityly.presentation.core.utils.fromHexToColor
import com.gvituskins.utilityly.presentation.core.utils.toHex

fun CategoryEntity.toCategory(parameters: List<ParameterCategoryEntity>): Category {
    return Category(
        id = id,
        name = name,
        description = description,
        color = Color("#${colorHex}".toColorInt()),
        parameters = parameters.map { it.toCategoryParameter() }
    )
}

data class ParameterWithValue(
    val parameterCategory: ParameterCategoryEntity,
    val value: ParameterValueEntity?
)

fun CategoryEntity.toCategoryWithValues(parameters: List<ParameterWithValue>): Category {
    return Category(
        id = id,
        name = name,
        description = description,
        color = colorHex.fromHexToColor(),
        parameters = parameters.map { it.parameterCategory.toCategoryParameter(it.value?.value) }
    )
}

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        description = description,
        colorHex = color.toHex()
    )
}

fun ParameterCategoryEntity.toCategoryParameter(value: String? = null): CategoryParameter {
    return CategoryParameter(
        id = id,
        name = name,
        value = value
    )
}

fun CategoryParameter.toCategoryParameterEntity(categoryId: Int = 0): ParameterCategoryEntity {
    return ParameterCategoryEntity(
        id = id,
        categoryId = categoryId,
        name = name,
    )
}
