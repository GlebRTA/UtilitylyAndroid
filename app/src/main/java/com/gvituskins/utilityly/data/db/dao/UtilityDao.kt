package com.gvituskins.utilityly.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.gvituskins.utilityly.data.db.entities.ParameterValueEntity
import com.gvituskins.utilityly.data.db.entities.UtilityEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface UtilityDao {

    @Query("SELECT * FROM utility WHERE locationId = :locationId ORDER BY dueDate DESC")
    fun getAllUtilities(locationId: Int): Flow<List<UtilityEntity>>

    @Query("SELECT * FROM utility WHERE locationId = :locationId AND dueDate BETWEEN :startDate AND :endDate")
    suspend fun getAllUtilitiesBetween(
        locationId: Int,
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<UtilityEntity>

    @Query("SELECT * FROM utility WHERE id == :id AND locationId = :locationId")
    suspend fun getById(id: Int, locationId: Int): UtilityEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNew(utility: UtilityEntity): Long

    @Update
    suspend fun updateUtility(utility: UtilityEntity)

    @Delete
    suspend fun deleteUtility(utility: UtilityEntity)

    @Query("DELETE FROM utility WHERE id = :utilityId")
    suspend fun deleteById(utilityId: Int)

    @Query(
        """
    SELECT * FROM utility
    WHERE categoryId = :categoryId
      AND locationId = :locationId
      AND paidStatus = 'PAID'
      AND dueDate <= :date
      AND id != :utilityId
    ORDER BY dueDate DESC
    LIMIT 1
"""
    )
    suspend fun getLastPaidUtilityByCategoryBeforeDate(
        categoryId: Int,
        locationId: Int,
        date: LocalDate,
        utilityId: Int,
    ): UtilityEntity?

    @Query("SELECT * FROM parameter_value WHERE utilityId = :utilityId AND categoryParameterId = :categoryParamId")
    suspend fun getParametersValue(utilityId: Int, categoryParamId: Int): ParameterValueEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addParameterValue(value: ParameterValueEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParameterValues(values: List<ParameterValueEntity>)

    @Transaction
    suspend fun addNewUtilityWithParametersValues(
        utility: UtilityEntity,
        paramValues: List<ParameterValueEntity>,
    ) {
        val utilityId = addNew(utility)
        insertParameterValues(paramValues.map { it.copy(utilityId = utilityId.toInt()) })
    }

    @Transaction
    suspend fun updateUtilityWithParametersValues(
        utility: UtilityEntity,
        paramValues: List<ParameterValueEntity>,
    ) {
        updateUtility(utility)
        insertParameterValues(paramValues)
    }
}
