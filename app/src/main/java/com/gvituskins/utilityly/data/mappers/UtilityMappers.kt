package com.gvituskins.utilityly.data.mappers

import com.gvituskins.utilityly.data.db.entities.CategoryEntity
import com.gvituskins.utilityly.data.db.entities.CategoryWithParameterCategory
import com.gvituskins.utilityly.data.db.entities.CompanyEntity
import com.gvituskins.utilityly.data.db.entities.LocationEntity
import com.gvituskins.utilityly.data.db.entities.UtilityEntity
import com.gvituskins.utilityly.domain.models.utilities.Utility

fun UtilityEntity.toUtility(
    categoryWithParameters: CategoryWithParameterCategory,
    company: CompanyEntity?,
    location: LocationEntity
): Utility {
    return Utility(
        id = id,
        category = categoryWithParameters.category.toCategory(categoryWithParameters.parameters),
        company = company?.toCompany(),
        repeat = repeat,
        amount = amount,
        location = location.toLocation(),
        dateCreated = dateCreated,
        paidStatus = paidStatus,
        dueDate = dueDate,
        datePaid = datePaid,
        previousUtilityId = previousUtilityId
    )
}

fun UtilityEntity.toUtility(
    category: CategoryEntity,
    parametersWithValues: List<ParameterWithValue>,
    company: CompanyEntity?,
    location: LocationEntity
): Utility {
    return Utility(
        id = id,
        category = category.toCategoryWithValues(parametersWithValues),
        company = company?.toCompany(),
        repeat = repeat,
        amount = amount,
        location = location.toLocation(),
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
        categoryId = category.id,
        companyId = company?.id,
        repeat = repeat,
        amount = amount,
        locationId = location.id,
        dateCreated = dateCreated,
        paidStatus = paidStatus,
        dueDate = dueDate,
        datePaid = datePaid,
        previousUtilityId = previousUtilityId
    )
}
