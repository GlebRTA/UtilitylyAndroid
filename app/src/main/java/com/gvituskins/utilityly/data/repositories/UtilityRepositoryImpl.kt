package com.gvituskins.utilityly.data.repositories

import com.gvituskins.utilityly.data.db.dao.CategoryDao
import com.gvituskins.utilityly.data.db.dao.CompanyDao
import com.gvituskins.utilityly.data.db.dao.LocationDao
import com.gvituskins.utilityly.data.db.dao.UtilityDao
import com.gvituskins.utilityly.data.db.entities.ParameterValueEntity
import com.gvituskins.utilityly.data.db.entities.UtilityEntity
import com.gvituskins.utilityly.data.mappers.ParameterWithValue
import com.gvituskins.utilityly.data.mappers.toUtility
import com.gvituskins.utilityly.data.mappers.toUtilityEntity
import com.gvituskins.utilityly.data.preferences.DataStoreUtil
import com.gvituskins.utilityly.domain.models.utilities.Utility
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class UtilityRepositoryImpl @Inject constructor(
    private val utilityDao: UtilityDao,
    private val categoryDao: CategoryDao,
    private val companyDao: CompanyDao,
    private val locationDao: LocationDao,
    private val preferences: DataStoreUtil
) : UtilityRepository {

    override suspend fun getUtilityById(id: Int): Utility {
        return utilityDao.getById(id, preferences.getCurrentLocationId()).toUtility()
    }

    private suspend fun UtilityEntity.toUtility(): Utility {
        return this.let { utilityDb ->
            val categoryParameters = categoryDao.getCategoryParameters(utilityDb.categoryId)

            val paramsWithValues = categoryParameters.parameters.map { categoryParameter ->
                ParameterWithValue(
                    parameterCategory = categoryParameter,
                    value = utilityDao.getParametersValue(
                        utilityId = utilityDb.id,
                        categoryParamId = categoryParameter.id
                    )
                )
            }

            utilityDb.toUtility(
                category = categoryParameters.category,
                parametersWithValues = paramsWithValues,
                company = utilityDb.companyId?.let { companyDao.getCompanyById(it) },
                location = locationDao.getLocationById(preferences.getCurrentLocationId())
            )
        }
    }

    override suspend fun getAllUtilities(): Flow<List<Utility>> {
        return utilityDao.getAllUtilities(preferences.getCurrentLocationId())
            .map { utilitiesDb -> utilitiesDb.toUtilities() }
    }

    override suspend fun getAllUtilitiesByYear(year: Int): List<Utility> {
        val start = LocalDate.of(year, 1, 1)
        val end = LocalDate.of(year, 12, 31)

        return utilityDao.getAllUtilitiesBetween(
            locationId = preferences.getCurrentLocationId(),
            startDate = start,
            endDate = end
        )
            .toUtilities()
    }

    override suspend fun getAllUtilitiesByMonth(month: Int, year: Int): List<Utility> {
        val (start, end) = getStartAndEndOfMonth(year = year, month = month)
        return utilityDao.getAllUtilitiesBetween(
            locationId = preferences.getCurrentLocationId(),
            startDate = start,
            endDate = end
        )
            .toUtilities()
    }

    private fun getStartAndEndOfMonth(year: Int, month: Int): Pair<LocalDate, LocalDate> {
        val start = LocalDate.of(year, month, 1)
        val end = start.withDayOfMonth(start.lengthOfMonth())
        return start to end
    }

    override suspend fun getAllUtilitiesByDay(day: Int, month: Int, year: Int): List<Utility> {
        val date = LocalDate.of(year, month, day)
        return utilityDao.getAllUtilitiesBetween(
            locationId = preferences.getCurrentLocationId(),
            startDate = date,
            endDate = date
        )
            .toUtilities()
    }

    private suspend fun List<UtilityEntity>.toUtilities(): List<Utility> = coroutineScope {
        val locationDeferred = async {
            locationDao.getLocationById(preferences.getCurrentLocationId())
        }

        val categoryIds = map { it.categoryId }.toSet()
        val companyIds = mapNotNull { it.companyId }.toSet()

        val categoriesMap = categoryIds
            .associateWith { categoryId ->
                async { categoryDao.getCategoryParameters(categoryId) }
            }
            .mapValues { it.value.await() }

        val companiesMap = companyIds
            .associateWith { companyId ->
                async { companyDao.getCompanyById(companyId) }
            }
            .mapValues { it.value.await() }

        val location = locationDeferred.await()

        mapNotNull { utilityEntity ->
            categoriesMap[utilityEntity.categoryId]?.let {
                utilityEntity.toUtility(
                    categoryWithParameters = it,
                    company = utilityEntity.companyId?.let { companiesMap[it] },
                    location = location
                )
            }
        }
    }

    override suspend fun addNewUtility(utility: Utility) {
        val paramValues = utility.category.parameters.map { categoryParameter ->
            ParameterValueEntity(
                categoryParameterId = categoryParameter.id,
                value = categoryParameter.value ?: "",
                utilityId = 0 //Will be added in DTO
            )
        }

        utilityDao.addNewUtilityWithParametersValues(
            utility = utility.toUtilityEntity(),
            paramValues = paramValues
        )
    }

    override suspend fun updateUtility(utility: Utility) {
        val paramValues = utility.category.parameters.map { categoryParameter ->
            val value = utilityDao.getParametersValue(
                utilityId = utility.id,
                categoryParamId = categoryParameter.id
            )

            if (value != null) {
                value.copy(
                    value = categoryParameter.value ?: ""
                )
            } else {
                ParameterValueEntity(
                    categoryParameterId = categoryParameter.id,
                    value = categoryParameter.value ?: "",
                    utilityId = utility.id
                )
            }
        }

        utilityDao.updateUtilityWithParametersValues(
            utility = utility.toUtilityEntity(),
            paramValues = paramValues
        )
    }

    override suspend fun deleteUtility(utilityId: Int) {
        utilityDao.deleteById(utilityId)
    }

    override suspend fun changePaidStatus(utilityId: Int) {
        val utility = getUtilityById(utilityId)
        val newPaidStatus = utility.paidStatus.otherwise()
        val paidDate = if (newPaidStatus.isPaid) LocalDate.now() else null
        updateUtility(
            utility.copy(
                paidStatus = newPaidStatus,
                datePaid = paidDate
            )
        )
    }

    override suspend fun getPreviousUtility(
        utilityId: Int,
        categoryId: Int,
        date: LocalDate
    ): Utility? {
        return utilityDao.getLastPaidUtilityByCategoryBeforeDate(
            categoryId = categoryId,
            locationId = preferences.getCurrentLocationId(),
            date = date,
            utilityId = utilityId
        )?.toUtility()
    }

    override suspend fun getAllAvailableYears(): List<Int> {
        return utilityDao.getAllUtilities(preferences.getCurrentLocationId())
            .first()
            .map { it.dueDate.year }
            .distinct()
    }
}
