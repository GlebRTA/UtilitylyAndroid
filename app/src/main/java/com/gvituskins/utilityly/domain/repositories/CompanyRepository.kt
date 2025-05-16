package com.gvituskins.utilityly.domain.repositories

import com.gvituskins.utilityly.domain.models.common.Either
import com.gvituskins.utilityly.domain.models.companies.Company
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {

    fun getAllCompanies(): Flow<List<Company>>

    suspend fun getCompanyById(id: Int): Company

    suspend fun addCompany(company: Company): Either<Unit, String>

    suspend fun updateCompany(company: Company): Either<Unit, String>

    suspend fun deleteCompany(company: Company): Either<Unit, String>
}
