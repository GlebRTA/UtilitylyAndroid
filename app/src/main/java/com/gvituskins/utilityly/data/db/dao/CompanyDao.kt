package com.gvituskins.utilityly.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gvituskins.utilityly.data.db.entities.CompanyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyDao {

    @Query("SELECT * FROM company")
    fun getAllCompanies(): Flow<List<CompanyEntity>>

    @Query("SELECT * FROM company WHERE id = :id")
    suspend fun getCompanyById(id: Int): CompanyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCompany(company: CompanyEntity)

    @Update
    suspend fun updateCompany(company: CompanyEntity)

    @Delete
    suspend fun deleteCompany(company: CompanyEntity)
}
