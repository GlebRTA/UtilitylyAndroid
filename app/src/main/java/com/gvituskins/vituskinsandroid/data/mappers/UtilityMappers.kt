package com.gvituskins.vituskinsandroid.data.mappers

import com.gvituskins.vituskinsandroid.data.db.entities.UtilityEntity
import com.gvituskins.vituskinsandroid.domain.models.Utility

fun UtilityEntity.toUtility(): Utility {
    return Utility(
        id = id,
        name = name,
        description = description,
        date = date,
        isPaid = isPaid
    )
}

fun Utility.toUtilityEntity(): UtilityEntity {
    return UtilityEntity(
        id = id ?: 0,
        name = name,
        description = description,
        date = date,
        isPaid = isPaid
    )
}
