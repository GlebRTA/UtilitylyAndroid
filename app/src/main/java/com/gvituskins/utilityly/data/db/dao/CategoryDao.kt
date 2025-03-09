package com.gvituskins.utilityly.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.gvituskins.utilityly.data.db.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAllCategories(): Flow<List<CategoryEntity>>
}
