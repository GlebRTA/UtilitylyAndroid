package com.gvituskins.vituskinsandroid.data.repositories

import com.gvituskins.vituskinsandroid.data.db.dao.UtilityDao
import com.gvituskins.vituskinsandroid.data.mappers.toUtility
import com.gvituskins.vituskinsandroid.data.mappers.toUtilityEntity
import com.gvituskins.vituskinsandroid.domain.models.Utility
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UtilityRepository @Inject constructor(
    private val utilityDao: UtilityDao
) {

    suspend fun getUtilityById(id: Int): Utility {
        return utilityDao.getById(id).toUtility()
    }

    fun getAllUnpaidUtilities(): Flow<List<Utility>> {
        return utilityDao.getAllUnpaid().map { utilitiesDb ->
            utilitiesDb.map { utilityEntity ->  utilityEntity.toUtility() }
        }
    }

    fun getAllPaidUtilities(): Flow<List<Utility>> {
        return utilityDao.getAllPaid().map { utilitiesDb ->
            utilitiesDb.map { utilityEntity ->  utilityEntity.toUtility() }
        }
    }

    suspend fun addNewUtility(utility: Utility) {
        utilityDao.addNew(utility.toUtilityEntity())
    }

    suspend fun updateUtility(utility: Utility) {
        utilityDao.updateUtility(utility.toUtilityEntity())
    }

    suspend fun changePaidStatus(utilityId: Int) {
        val utility = getUtilityById(utilityId)
        addNewUtility(utility.copy(isPaid = !utility.isPaid))
    }
}
