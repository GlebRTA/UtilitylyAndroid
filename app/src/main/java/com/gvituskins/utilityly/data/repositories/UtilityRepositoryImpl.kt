package com.gvituskins.utilityly.data.repositories

import com.gvituskins.utilityly.data.db.dao.UtilityDao
import com.gvituskins.utilityly.data.mappers.toUtility
import com.gvituskins.utilityly.data.mappers.toUtilityEntity
import com.gvituskins.utilityly.domain.models.enums.PaidStatus
import com.gvituskins.utilityly.domain.models.utilities.Utility
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UtilityRepositoryImpl @Inject constructor(
    private val utilityDao: UtilityDao
) : UtilityRepository {

    override suspend fun getUtilityById(id: Int): Utility {
        return utilityDao.getById(id).toUtility()
    }

    override fun getAllUnpaidUtilities(): Flow<List<Utility>> {
        return utilityDao.getAllPaid(PaidStatus.UNPAID).map { utilitiesDb ->
            utilitiesDb.map { utilityEntity ->  utilityEntity.toUtility() }
        }
    }

    override fun getAllPaidUtilities(): Flow<List<Utility>> {
        return utilityDao.getAllPaid(PaidStatus.PAID).map { utilitiesDb ->
            utilitiesDb.map { utilityEntity ->  utilityEntity.toUtility() }
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
        return utilityDao.getLastPaidUtilityByCategory(categoryId)?.toUtility()
    }
}
