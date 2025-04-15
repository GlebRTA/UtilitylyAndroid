package com.gvituskins.utilityly.data.mappers

import com.gvituskins.utilityly.data.db.entities.UtilityEntity
import com.gvituskins.utilityly.domain.models.utilities.Utility

fun UtilityEntity.toUtility(): Utility {
    return Utility(
        id = id,
        categoryId = categoryId,
        companyId = companyId,
        repeat = repeat,
        amount = amount,
        locationId = locationId,
        dateCreated = dateCreated,
        paidStatus = paidStatus,
        dueDate = dueDate,
        datePaid = datePaid,
        previousUtilityId = previousUtilityId
    )
}

fun Utility.toUtilityEntity(): UtilityEntity {
    return UtilityEntity(
        id = id,
        categoryId = categoryId,
        companyId = companyId,
        repeat = repeat,
        amount = amount,
        locationId = locationId,
        dateCreated = dateCreated,
        paidStatus = paidStatus,
        dueDate = dueDate,
        datePaid = datePaid,
        previousUtilityId = previousUtilityId
    )
}
