package com.gvituskins.utilityly.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.gvituskins.utilityly.data.db.entities.CategoryEntity
import com.gvituskins.utilityly.data.db.entities.CategoryWithParameterCategory
import com.gvituskins.utilityly.data.db.entities.ParameterCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM category WHERE id = :id")
    suspend fun getCategoryById(id: Int): CategoryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewCategory(category: CategoryEntity): Long

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    @Transaction
    @Query("SELECT * FROM category WHERE id = :categoryId")
    suspend fun getCategoryParameters(categoryId: Int): CategoryWithParameterCategory

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategoryParameter(parameter: ParameterCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParameters(parameters: List<ParameterCategoryEntity>)

    @Update
    suspend fun updateParameters(parameters: List<ParameterCategoryEntity>)

    @Delete
    suspend fun deleteParameters(parameters: List<ParameterCategoryEntity>)

    @Transaction
    suspend fun addCategoryWithParameters(
        category: CategoryEntity,
        parameters: List<ParameterCategoryEntity>
    ) {
        val categoryId = addNewCategory(category)

        val updatedParameters = parameters.map { it.copy(categoryId = categoryId.toInt()) }
        insertParameters(updatedParameters)
    }

    @Transaction
    suspend fun syncCategoryWithParameters(
        category: CategoryEntity,
        parameters: List<ParameterCategoryEntity>
    ) {
        updateCategory(category)
        val existingParams = getCategoryParameters(categoryId = category.id).parameters

        val toInsert = parameters.mapNotNull { newParam ->
            if (newParam.id == 0) {
                newParam.copy(categoryId = category.id)
            } else null
        }

        val toUpdate = parameters.mapNotNull { newParam ->
            val existing = existingParams.find { it.id == newParam.id }
            if (existing != null) newParam else null
        }

        val newIds = parameters.mapNotNull { if (it.id != 0) it.id else null }.toSet()
        val toDelete = existingParams.filterNot { it.id in newIds }

        updateParameters(toUpdate)
        insertParameters(toInsert)
        deleteParameters(toDelete)
    }
}
