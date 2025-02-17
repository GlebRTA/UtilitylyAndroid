package com.gvituskins.utilityly.data.repositories

import com.gvituskins.utilityly.data.db.dao.UtilityDao
import com.gvituskins.utilityly.data.mappers.toUtility
import com.gvituskins.utilityly.data.mappers.toUtilityEntity
import com.gvituskins.utilityly.data.network.api.NinjaApiService
import com.gvituskins.utilityly.data.network.utils.apiCall
import com.gvituskins.utilityly.domain.models.Fact
import com.gvituskins.utilityly.domain.models.utilities.Utility
import com.gvituskins.utilityly.domain.models.common.NetworkResult
import com.gvituskins.utilityly.domain.models.utilities.CreateUtility
import com.gvituskins.utilityly.domain.repositories.UtilityRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityRetainedScoped
class UtilityRepositoryImpl @Inject constructor(
    private val utilityDao: UtilityDao,
    private val ninjaApiService: NinjaApiService
) : UtilityRepository {

    override suspend fun getUtilityById(id: Int): Utility {
        return utilityDao.getById(id).toUtility()
    }

    override fun getAllUnpaidUtilities(): Flow<List<Utility>> {
        return utilityDao.getAllUnpaid().map { utilitiesDb ->
            utilitiesDb.map { utilityEntity ->  utilityEntity.toUtility() }
        }
    }

    override fun getAllPaidUtilities(): Flow<List<Utility>> {
        return utilityDao.getAllPaid().map { utilitiesDb ->
            utilitiesDb.map { utilityEntity ->  utilityEntity.toUtility() }
        }
    }

    override suspend fun addNewUtility(utility: CreateUtility) {
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
        updateUtility(utility.copy(isPaid = !utility.isPaid))
    }

    override suspend fun getRandomFact(): NetworkResult<Fact> {
        return apiCall(
            mapper = { Fact(it.first().fact) },
            callback = { ninjaApiService.getRandomFacts() }
        )
    }
}
