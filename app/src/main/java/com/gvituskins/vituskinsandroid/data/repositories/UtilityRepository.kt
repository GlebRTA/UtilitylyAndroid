package com.gvituskins.vituskinsandroid.data.repositories

import com.gvituskins.vituskinsandroid.data.db.dao.UtilityDao
import com.gvituskins.vituskinsandroid.data.mappers.toUtility
import com.gvituskins.vituskinsandroid.data.mappers.toUtilityEntity
import com.gvituskins.vituskinsandroid.domain.models.Utility
import javax.inject.Inject

class UtilityRepository @Inject constructor(
    private val utilityDao: UtilityDao
) {

    suspend fun getUtilityById(id: Int): Utility {
        return utilityDao.getById(id).toUtility()
    }

    suspend fun getAllUnpaidUtilities(): List<Utility> {
        return utilityDao.getAllUnpaid().map { it.toUtility() }
    }

    suspend fun addNewUtility(utility: Utility) {
        utilityDao.addNew(utility.toUtilityEntity())
    }
}
