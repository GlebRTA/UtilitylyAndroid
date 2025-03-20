package com.gvituskins.utilityly.data.repositories

import com.gvituskins.utilityly.data.db.dao.CompanyDao
import com.gvituskins.utilityly.data.mappers.toCompany
import com.gvituskins.utilityly.data.mappers.toCompanyEntity
import com.gvituskins.utilityly.domain.models.companies.Company
import com.gvituskins.utilityly.domain.repositories.CompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyDao: CompanyDao
) : CompanyRepository{
    override fun getAllCompanies(): Flow<List<Company>> {
        return companyDao.getAllCompanies().map { companies ->
            companies.map { it.toCompany() }
        }
    }

    override suspend fun getCompanyById(id: Int): Company {
        return companyDao.getCompanyById(id).toCompany()
    }

    override suspend fun addCompany(company: Company) {
        companyDao.addCompany(company.toCompanyEntity())
    }

    override suspend fun updateCompany(company: Company) {
        companyDao.updateCompany(company.toCompanyEntity())
    }

    override suspend fun deleteCompany(company: Company) {
        companyDao.deleteCompany(company.toCompanyEntity())
    }
}
