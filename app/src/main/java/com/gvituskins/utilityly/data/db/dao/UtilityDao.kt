package com.gvituskins.utilityly.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gvituskins.utilityly.data.db.entities.ParameterValueEntity
import com.gvituskins.utilityly.data.db.entities.UtilityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UtilityDao {

    @Query("SELECT * FROM utility WHERE locationId = :locationId")
    fun getAllUtilities(locationId: Int): Flow<List<UtilityEntity>>

    @Query("SELECT * FROM utility WHERE id == :id AND locationId = :locationId")
    suspend fun getById(id: Int, locationId: Int): UtilityEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNew(utility: UtilityEntity): Long

    @Update
    suspend fun updateUtility(utility: UtilityEntity)

    @Delete
    suspend fun delete(utility: UtilityEntity)

    @Query("DELETE FROM utility WHERE id = :utilityId")
    suspend fun deleteById(utilityId: Int)

    @Query("SELECT * FROM utility WHERE categoryId = :categoryId AND locationId = :locationId AND paidStatus = 'PAID' ORDER BY dueDate DESC")
    suspend fun getLastPaidUtilityByCategory(categoryId: Int, locationId: Int): List<UtilityEntity>?

    @Query("SELECT * FROM parameter_value WHERE utilityId = :utilityId AND categoryParameterId = :categoryParamId")
    suspend fun getParametersValue(utilityId: Int, categoryParamId: Int): ParameterValueEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addParameterValue(value: ParameterValueEntity)
}
