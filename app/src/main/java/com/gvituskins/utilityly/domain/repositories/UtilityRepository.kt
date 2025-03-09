package com.gvituskins.utilityly.domain.repositories

import com.gvituskins.utilityly.domain.models.utilities.CreateUtility
import com.gvituskins.utilityly.domain.models.utilities.Utility
import kotlinx.coroutines.flow.Flow

interface UtilityRepository {
    suspend fun getUtilityById(id: Int): Utility

    fun getAllUnpaidUtilities(): Flow<List<Utility>>

    fun getAllPaidUtilities(): Flow<List<Utility>>

    suspend fun addNewUtility(utility: CreateUtility)

    suspend fun updateUtility(utility: Utility)

    suspend fun deleteUtility(utilityId: Int)

    suspend fun changePaidStatus(utilityId: Int)
}
