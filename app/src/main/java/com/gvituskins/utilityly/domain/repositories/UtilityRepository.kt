package com.gvituskins.utilityly.domain.repositories

import com.gvituskins.utilityly.domain.models.utilities.Utility
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface UtilityRepository {
    suspend fun getUtilityById(id: Int): Utility

    suspend fun getAllUtilities(): Flow<List<Utility>>

    suspend fun getAllUtilitiesByYear(year: Int): List<Utility>

    suspend fun getAllUtilitiesByMonth(month: Int, year: Int): List<Utility>

    suspend fun getAllUtilitiesByDay(day: Int, month: Int, year: Int): List<Utility>

    suspend fun addNewUtility(utility: Utility)

    suspend fun updateUtility(utility: Utility)

    suspend fun deleteUtility(utilityId: Int)

    suspend fun changePaidStatus(utilityId: Int)

    suspend fun getPreviousUtility(utilityId: Int, categoryId: Int, date: LocalDate): Utility?

    suspend fun getAllAvailableYears(): List<Int>

    suspend fun addParameterValue(value: String, utilityId: Int, categoryId: Int)
}
