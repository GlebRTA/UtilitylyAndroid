package com.gvituskins.utilityly.data.mappers

import com.gvituskins.utilityly.data.db.entities.CompanyEntity
import com.gvituskins.utilityly.domain.models.companies.Company

fun Company.toCompanyEntity(): CompanyEntity {
    return CompanyEntity(
        id = id,
        name = name,
        address = address,
        phone = phone,
        webPageUrl = webPageUrl,
        email = email
    )
}

fun CompanyEntity.toCompany(): Company {
    return Company(
        id = id,
        name = name,
        address = address,
        phone = phone,
        webPageUrl = webPageUrl,
        email = email
    )
}