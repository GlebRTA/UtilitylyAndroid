package com.gvituskins.vituskinsandroid.domain.repositories

import com.gvituskins.vituskinsandroid.domain.models.Utility
import kotlinx.coroutines.flow.Flow

interface UtilityRepository {
    suspend fun getUtilityById(id: Int): Utility

    fun getAllUnpaidUtilities(): Flow<List<Utility>>

    fun getAllPaidUtilities(): Flow<List<Utility>>

    suspend fun addNewUtility(utility: Utility)

    suspend fun updateUtility(utility: Utility)

    suspend fun changePaidStatus(utilityId: Int)
}
