package com.gvituskins.utilityly.data.mappers

import com.gvituskins.utilityly.data.db.entities.CategoryEntity
import com.gvituskins.utilityly.data.db.entities.ParameterCategoryEntity
import com.gvituskins.utilityly.domain.models.categories.Category
import com.gvituskins.utilityly.domain.models.categories.CategoryParameter

fun CategoryEntity.toCategory(parameters: List<ParameterCategoryEntity>): Category {
    return Category(
        id = id,
        name = name,
        description = description,
        iconUrl = iconUrl,
        parameters = parameters.map { it.toCategoryParameter() }
    )
}

fun ParameterCategoryEntity.toCategoryParameter(): CategoryParameter {
    return CategoryParameter(
        name = name
    )
}

fun CategoryParameter.toCategoryParameterEntity(categoryId: Int = 0): ParameterCategoryEntity {
    return ParameterCategoryEntity(
        categoryId = categoryId,
        name = name,
    )
}

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        description = description,
        iconUrl = iconUrl
    )
}
