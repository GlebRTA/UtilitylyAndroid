package com.gvituskins.utilityly.domain.repositories

import com.gvituskins.utilityly.domain.models.utilities.Utility
import kotlinx.coroutines.flow.Flow

interface UtilityRepository {
    suspend fun getUtilityById(id: Int): Utility

    fun getAllUtilities(): Flow<List<Utility>>

    suspend fun getAllUtilitiesByMonth(month: Int, year: Int): List<Utility>

    suspend fun getAllUtilitiesByDay(day: Int, month: Int, year: Int): List<Utility>

    suspend fun addNewUtility(utility: Utility)

    suspend fun updateUtility(utility: Utility)

    suspend fun deleteUtility(utilityId: Int)

    suspend fun changePaidStatus(utilityId: Int)

    suspend fun getPreviousUtility(categoryId: Int): Utility?
}
