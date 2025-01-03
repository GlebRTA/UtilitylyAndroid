package com.gvituskins.utilityhelper.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gvituskins.utilityhelper.data.db.entities.UtilityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UtilityDao {

    @Query("SELECT * FROM utility WHERE isPaid == 1")
    fun getAllPaid(): Flow<List<UtilityEntity>>

    @Query("SELECT * FROM utility WHERE isPaid == 0")
    fun getAllUnpaid(): Flow<List<UtilityEntity>>

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
}
