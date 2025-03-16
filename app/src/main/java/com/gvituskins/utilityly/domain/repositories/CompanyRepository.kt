package com.gvituskins.utilityly.domain.repositories

import com.gvituskins.utilityly.domain.models.companies.Company
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {

    fun getAllCompanies(): Flow<List<Company>>

    suspend fun addCompany(company: Company)

    suspend fun updateCompany(company: Company)

    suspend fun deleteCompany(company: Company)
}
