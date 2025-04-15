package com.gvituskins.utilityly.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gvituskins.utilityly.data.db.entities.UtilityEntity
import com.gvituskins.utilityly.domain.models.enums.PaidStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface UtilityDao {

    @Query("SELECT * FROM utility WHERE paidStatus == :status")
    fun getAllPaid(status: PaidStatus): Flow<List<UtilityEntity>>

    @Query("SELECT * FROM utility WHERE id == :id")
    suspend fun getById(id: Int): UtilityEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNew(utility: UtilityEntity)

    @Update
    suspend fun updateUtility(utility: UtilityEntity)

    @Delete
    suspend fun delete(utility: UtilityEntity)

    @Query("DELETE FROM utility WHERE id = :utilityId")
    suspend fun deleteById(utilityId: Int)

    @Query("SELECT * FROM utility WHERE categoryId = :categoryId AND paidStatus = 'PAID' ORDER BY dueDate DESC LIMIT 1")
    suspend fun getLastPaidUtilityByCategory(categoryId: Int): UtilityEntity?
}
