package com.gvituskins.vituskinsandroid.domain.repositories

import com.gvituskins.vituskinsandroid.domain.models.Fact
import com.gvituskins.vituskinsandroid.domain.models.utilities.Utility
import com.gvituskins.vituskinsandroid.domain.models.common.NetworkResult
import com.gvituskins.vituskinsandroid.domain.models.utilities.CreateUtility
import kotlinx.coroutines.flow.Flow

interface UtilityRepository {
    suspend fun getUtilityById(id: Int): Utility

    fun getAllUnpaidUtilities(): Flow<List<Utility>>

    fun getAllPaidUtilities(): Flow<List<Utility>>

    suspend fun addNewUtility(utility: CreateUtility)

    suspend fun updateUtility(utility: Utility)

    suspend fun deleteUtility(utilityId: Int)

    suspend fun changePaidStatus(utilityId: Int)

    suspend fun getRandomFact(): NetworkResult<Fact>
}
