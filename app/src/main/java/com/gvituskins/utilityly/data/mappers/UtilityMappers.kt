package com.gvituskins.utilityly.data.mappers

import com.gvituskins.utilityly.data.db.entities.UtilityEntity
import com.gvituskins.utilityly.domain.models.utilities.CreateUtility
import com.gvituskins.utilityly.domain.models.utilities.Utility
import java.util.Date

fun UtilityEntity.toUtility(): Utility {
    return Utility(
        id = id,
        name = name,
        description = description,
        date = date,
        isPaid = isPaid,
        amount = amount
    )
}

fun Utility.toUtilityEntity(): UtilityEntity {
    return UtilityEntity(
        id = id,
        name = name,
        description = description,
        date = date,
        isPaid = isPaid,
        amount = amount
    )
}

fun CreateUtility.toUtilityEntity(): UtilityEntity {
    return UtilityEntity(
        id = 0,
        name = name,
        description = description,
        date = Date().toLocaleString(),
        isPaid = false,
        amount = amount
    )
}
