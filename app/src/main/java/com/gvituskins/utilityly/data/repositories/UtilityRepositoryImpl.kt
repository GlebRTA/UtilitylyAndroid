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
        return utilityDao.getById(id, preferences.getCurrentLocationId()).let { utilityDb ->
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
        return utilityDao.getAllUtilities(preferences.getCurrentLocationId()).map { utilitiesDb -> utilitiesDb.toUtilities() }
    }

    override suspend fun getAllUtilitiesByYear(year: Int): List<Utility> {
        return utilityDao.getAllUtilities(preferences.getCurrentLocationId())
            .first()
            .filter { it.dueDate.year == year }
            .toUtilities()
    }

    override suspend fun getAllUtilitiesByMonth(month: Int, year: Int): List<Utility> {
        return utilityDao.getAllUtilities(preferences.getCurrentLocationId())
            .first()
            .filter {
                it.dueDate.year == year && it.dueDate.monthValue == month
            }
            .toUtilities()
    }

    override suspend fun getAllUtilitiesByDay(day: Int, month: Int, year: Int): List<Utility> {
        return utilityDao.getAllUtilities(preferences.getCurrentLocationId())
            .first()
            .filter {
                it.dueDate.year == year && it.dueDate.monthValue == month && it.dueDate.dayOfMonth == day
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
        //TODO(Create transaction)
        val utilityId = utilityDao.addNew(utility.toUtilityEntity())

        utility.category.parameters.forEach { categoryParameter ->
            utilityDao.addParameterValue(
                ParameterValueEntity(
                    categoryParameterId = categoryParameter.id,
                    value = categoryParameter.value ?: "",
                    utilityId = utilityId.toInt()
                )
            )
        }
    }

    override suspend fun updateUtility(utility: Utility) {
        //TODO(Stopped here create sync params values)
        utilityDao.updateUtility(utility.toUtilityEntity())
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
        return utilityDao.getLastPaidUtilityByCategory(categoryId, preferences.getCurrentLocationId())
            ?.find { it.dueDate <= date && it.id != utilityId }
            ?.let { utilityDb ->
                getUtilityById(utilityDb.id)
            }
    }

    override suspend fun getAllAvailableYears(): List<Int> {
        val years = mutableSetOf<Int>()
        getAllUtilities().first().forEach {
            years.add(it.dueDate.year)
        }
        return years.toList()
    }

    override suspend fun addParameterValue(value: String, utilityId: Int, categoryId: Int) {
        utilityDao.addParameterValue(
            ParameterValueEntity(
                categoryParameterId = categoryId,
                value = value,
                utilityId = utilityId
            )
        )
    }
}
