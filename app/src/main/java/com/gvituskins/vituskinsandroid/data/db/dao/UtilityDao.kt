package com.gvituskins.vituskinsandroid.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gvituskins.vituskinsandroid.data.db.entities.UtilityEntity

@Dao
interface UtilityDao {

    @Query("SELECT * FROM utility WHERE isPaid == 1")
    suspend fun getAllPaid(): List<UtilityEntity>

    @Query("SELECT * FROM utility WHERE isPaid == 0")
    suspend fun getAllUnpaid(): List<UtilityEntity>

    @Query("SELECT * FROM utility WHERE id == :id")
    suspend fun getById(id: Int): UtilityEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNew(utility: UtilityEntity)

    @Delete
    suspend fun delete(utility: UtilityEntity)
}
