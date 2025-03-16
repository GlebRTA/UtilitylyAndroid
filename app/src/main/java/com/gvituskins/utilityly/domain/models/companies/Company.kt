package com.gvituskins.utilityly.domain.models.companies

data class Company(
    val id: Int,
    val name: String,
    val address: String?,
    val phone: String?,
    val webPageUrl: String?,
    val email: String?
)
