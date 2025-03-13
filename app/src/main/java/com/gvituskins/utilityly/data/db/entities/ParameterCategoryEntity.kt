package com.gvituskins.utilityly.data.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "parameter_category")
data class ParameterCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val categoryId: Int,
    val name: String
)

data class CategoryWithParameterCategory(
    @Embedded val parameter: ParameterCategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val categories: List<CategoryEntity>
)
