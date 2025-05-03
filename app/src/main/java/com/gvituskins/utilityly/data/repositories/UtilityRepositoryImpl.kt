package com.gvituskins.utilityly.data.repositories

import com.gvituskins.utilityly.data.db.dao.CategoryDao
import com.gvituskins.utilityly.data.db.dao.CompanyDao
import com.gvituskins.utilityly.data.db.dao.LocationDao
import com.gvituskins.utilityly.data.db.dao.UtilityDao
import com.gvituskins.utilityly.data.db.entities.UtilityEntity
import com.gvituskins.utilityly.data.mappers.toUtility
import com.gvituskins.utilityly.data.mappers.toUtilityEntity
import com.gvituskins.utilityly.data.preferences.DataStoreUtil
import com.gvituskins.utilityly.domain.models.utilities.Utility
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UtilityRepositoryImpl @Inject constructor(
    private val utilityDao: UtilityDao,
    private val categoryDao: CategoryDao,
    private val companyDao: CompanyDao,
    private val locationDao: LocationDao,
    private val preferences: DataStoreUtil
) : UtilityRepository {

    override suspend fun getUtilityById(id: Int): Utility {
        return utilityDao.getById(id).let { utilityDb ->
            utilityDb.toUtility(
                categoryWithParameters = categoryDao.getCategoryParameters(utilityDb.categoryId),
                company = utilityDb.companyId?.let { companyDao.getCompanyById(it) },
                location = locationDao.getLocationById(preferences.getCurrentLocationId())
            )
        }
    }

    override fun getAllUtilities(): Flow<List<Utility>> {
        return utilityDao.getAllUtilities().map { utilitiesDb -> utilitiesDb.toUtilities() }
    }

    override suspend fun getAllUtilitiesByYear(year: Int): List<Utility> {
        return utilityDao.getAllUtilities()
            .first()
            .filter {
                (it.dueDate.year + 1900) == year
            }
            .toUtilities()
    }

    override suspend fun getAllUtilitiesByMonth(month: Int, year: Int): List<Utility> {
        return utilityDao.getAllUtilities()
            .first()
            .filter {
                (it.dueDate.year + 1900) == year && (it.dueDate.month + 1) == month
            }
            .toUtilities()
    }

    override suspend fun getAllUtilitiesByDay(day: Int, month: Int, year: Int): List<Utility> {
        return utilityDao.getAllUtilities()
            .first()
            .filter {
                (it.dueDate.year + 1900) == year && (it.dueDate.month + 1) == month && it.dueDate.date == day
            }
            .toUtilities()
    }

    private suspend fun List<UtilityEntity>.toUtilities(): List<Utility> {
        return map { utilityEntity ->
            utilityEntity.toUtility(
                categoryWithParameters = categoryDao.getCategoryParameters(utilityEntity.categoryId),
                company = utilityEntity.companyId?.let { companyDao.getCompanyById(it) },
                location = locationDao.getLocationById(preferences.getCurrentLocationId())
            )
        }
    }

    override suspend fun addNewUtility(utility: Utility) {
        utilityDao.addNew(utility.toUtilityEntity())
    }

    override suspend fun updateUtility(utility: Utility) {
        utilityDao.updateUtility(utility.toUtilityEntity())
    }

    override suspend fun deleteUtility(utilityId: Int) {
        utilityDao.deleteById(utilityId)
    }

    override suspend fun changePaidStatus(utilityId: Int) {
        val utility = getUtilityById(utilityId)
        updateUtility(utility.copy(paidStatus = utility.paidStatus.otherwise()))
    }

    override suspend fun getPreviousUtility(categoryId: Int): Utility? {
        return utilityDao.getLastPaidUtilityByCategory(categoryId)?.let { utilityDb ->
            utilityDb.toUtility(
                categoryWithParameters = categoryDao.getCategoryParameters(utilityDb.categoryId),
                company = utilityDb.companyId?.let { companyDao.getCompanyById(it) },
                location = locationDao.getLocationById(preferences.getCurrentLocationId())
            )
        }
    }

    override suspend fun getAllAvailableYears(): List<Int> {
        val years = mutableSetOf<Int>()
        getAllUtilities().first().forEach {
            years.add(it.dueDate.year + 1900)
        }
        return years.toList()
    }
}
