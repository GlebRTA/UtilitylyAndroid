package com.gvituskins.utilityly.data.mappers

import com.gvituskins.utilityly.data.db.entities.UtilityEntity
import com.gvituskins.utilityly.domain.models.enums.PaidStatus
import com.gvituskins.utilityly.domain.models.enums.UtilityRepeat
import com.gvituskins.utilityly.domain.models.utilities.CreateUtility
import com.gvituskins.utilityly.domain.models.utilities.Utility
import java.util.Date

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

fun CreateUtility.toUtilityEntity(): UtilityEntity {
    return UtilityEntity(
        id = 0,
        categoryId = 0,
        companyId = 0,
        repeat = UtilityRepeat.NONE,
        amount = amount,
        locationId = 0,
        dateCreated = Date(),
        paidStatus = PaidStatus.UNPAID,
        dueDate = Date(),
        datePaid = Date(),
        previousUtilityId = 0
    )
}
