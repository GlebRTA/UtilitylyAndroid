package com.gvituskins.utilityly.data.mappers

import com.gvituskins.utilityly.data.db.entities.CategoryEntity
import com.gvituskins.utilityly.domain.models.categories.Category

fun CategoryEntity.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        description = description,
        iconUrl = iconUrl
    )
}
